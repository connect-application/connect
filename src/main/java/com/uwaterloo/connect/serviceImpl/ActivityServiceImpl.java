package com.uwaterloo.connect.serviceImpl;

import com.uwaterloo.connect.model.Activity;
import com.uwaterloo.connect.model.ActivityChildMapping;
import com.uwaterloo.connect.model.ActivityRequest;
import com.uwaterloo.connect.model.Attachment;
import com.uwaterloo.connect.model.Post;
import com.uwaterloo.connect.model.ActivityRequest.Recurrence;
import com.uwaterloo.connect.repository.ActivityMappingRepository;
import com.uwaterloo.connect.repository.ActivityRepository;
import com.uwaterloo.connect.repository.AttachmentRepository;
import com.uwaterloo.connect.repository.PostRepository;
import com.uwaterloo.connect.security.UserActionAuthenticator;
import com.uwaterloo.connect.service.ActivityService;
import com.uwaterloo.connect.service.PostEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uwaterloo.connect.repository.ActivityScheduleRepository;
import com.uwaterloo.connect.model.ActivitySchedule;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.uwaterloo.connect.Constants.Constants.ERROR;
import static com.uwaterloo.connect.Constants.Constants.SUCCESS;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    PostEngine postEngine;

    @Autowired
    UserActionAuthenticator userActionAuthenticator;

    @Autowired
    ActivityScheduleRepository activityScheduleRepository;

    @Autowired
    ActivityMappingRepository activityMappingRepository;

    @Override
    public String createActivity(ActivityRequest activityRequest, Boolean isChild){
        try{
            //New post
            Integer postId = createPostForActivity(activityRequest);
            //Save attachments
            createAttachmentsforPost(postId, activityRequest.getFiles());
            //Make activity for the new post
            Activity activity = new Activity();
            activity.setPostId(postId);
            activity.setCategoryId(activityRequest.getCategoryId());
            activity.setStatusId(activityRequest.getStatusId());
            activity.setRecurring(activityRequest.isRecurring());
            if(activityRequest.isRecurring() && activityRequest.printRecurrence() != null){
                activity.setRecurrence(activityRequest.printRecurrence());
            }
            // Save dateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            if(isChild){
                activity.setStartTime(LocalDateTime.parse(LocalDateTime.now().format(formatter),formatter));
            } else {
                activity.setStartTime(LocalDateTime.parse(activityRequest.getStartTime(), formatter));
                activity.setEndTime(LocalDateTime.parse(activityRequest.getEndTime(), formatter));
            }
            //Save Activity
            activityRepository.save(activity);
            if(isChild){
                ActivityChildMapping activity_mapping = new ActivityChildMapping();
                activity_mapping.setActivityId(postId);
                activity_mapping.setParentActivityId(activityRequest.getParentId());
                activityMappingRepository.save(activity_mapping);
            } else {
                if(activityRequest.isRecurring()){
                    ActivitySchedule activitySchedule = new ActivitySchedule();
                    activitySchedule.setActivityId(postId);
                    activitySchedule.setNextRunDate(activityRequest.getRecurrence());
                    activityScheduleRepository.save(activitySchedule);
                }
            }

            return SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ERROR+e;
        }
    }
    public Integer createPostForActivity(ActivityRequest activityRequest){
        try{
            Integer userId = null;
            if(activityRequest.getParentId() != null){
                userId = activityRequest.getUserId();
            } else {
                userId = userActionAuthenticator.getLoggedUser().getId().intValue();
            }
            Post post = postEngine.createPost(userId, activityRequest.getPostText(), activityRequest.isShared());
            postRepository.save(post);
            return post.getPostId();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Integer> createAttachmentsforPost(Integer postId, List<byte[]> files){
        List<Integer> attachmentIds = new ArrayList<>();
        if(Objects.isNull(files)){
            return attachmentIds;
        }
        for(byte[] file: files){
            Attachment attachment = new Attachment();
            attachment.setPostId(postId);
            attachment.setFile(file);
            attachmentRepository.save(attachment);
            //Add AttachmentId to response list
            attachmentIds.add(attachment.getAttachmentId());
        }
        return attachmentIds;
    }

    @Override
    public String updateActivityStatus(Integer postId, Integer newStatusId){
        try{
            Activity activity = activityRepository.findActivityByPostId(postId);
            activity.setStatusId(newStatusId);
            activityRepository.save(activity);
            return SUCCESS;
        }catch (Exception e){
            System.out.println(e);
            return ERROR+e;
        }
    }

    @Override
    public String updateActivityStartTime(Integer postId, String newStartTime){
        try{
            Activity activity = activityRepository.findActivityByPostId(postId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            // Parse the string into a LocalDateTime object
            activity.setStartTime(LocalDateTime.parse(newStartTime, formatter));
            activityRepository.save(activity);
            return SUCCESS;
        }catch (Exception e){
            System.out.println(e);
            return ERROR+e;
        }
    }

    @Override
    public String updateActivityCategory(Integer postId, Integer newCategoryId){
        try{
            Activity activity = activityRepository.findActivityByPostId(postId);
            activity.setCategoryId(newCategoryId);
            activityRepository.save(activity);
            return SUCCESS;
        }catch (Exception e){
            System.out.println(e);
            return ERROR+e;
        }
    }

    @Override
    public String updateActivityRecurrence(Integer postId){
        try{
            Activity activity = activityRepository.findActivityByPostId(postId);
            activity.setRecurring(!activity.isRecurring());
            activityRepository.save(activity);
            return SUCCESS;
        }catch (Exception e){
            System.out.println(e);
            return ERROR+e;
        }
    }

    @Override
    public String deleteActivity(Integer postId){
        try{
            Activity activity = activityRepository.findActivityByPostId(postId);
            List<ActivityChildMapping> childActivity = activityMappingRepository.findChildActivitiesByParentId(postId);
            ActivitySchedule schedule = activityScheduleRepository.findScheduledActivityByPostId(postId);
            if(schedule != null) activityScheduleRepository.delete(schedule);
            List<Activity> activities = new ArrayList<>();
            activities.add(activity);
            if(childActivity.size() > 0) {
                for(ActivityChildMapping act : childActivity){
                    Activity child = activityRepository.findActivityByPostId(act.getActivityId());
                    if(child != null){
                        activities.add(child);
                    }
                }
                activityMappingRepository.deleteAll(childActivity);           
            }
            activityRepository.deleteAll(activities);
            return SUCCESS;
        }catch (Exception e){
            return ERROR+e;
        }
    }

    @Override
    @Transactional
    public String runScheduledActivities(){
        try{
            LocalDateTime currentDateTime = LocalDateTime.now();
            List<ActivitySchedule> as = activityScheduleRepository.findAllByNextRunDateBeforeOrEqualTo(currentDateTime);
            if(as.size() > 0){
                for(ActivitySchedule activity: as){
                    Activity parent = activityRepository.findActivityByPostId(activity.getActivityId());
                    if(parent == null){
                        activity.setNextRunDateAsNull();
                    } else {
                        ActivityRequest activityRequest = new ActivityRequest(parent);
                        Integer userId = postRepository.findUserIdByPostId(parent.getPostId());
                        activityRequest.setUserId(userId);
                        createActivity(activityRequest, true);
                        if(parent.getRecurrence() != null){
                            String startDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
                            Recurrence recurrence = parent.parseRecurrence(parent.getRecurrence());
                            recurrence.setStartDate(startDate.toString());
                            activity.setNextRunDate(recurrence);
                        } 
                    }
                }
                activityScheduleRepository.deleteActivities();
            }
            return SUCCESS;
        } catch (Exception e){
            e.printStackTrace();
            return ERROR+e;
        }
    }
    public String printRecurrence(Recurrence recurrence) {
        if (recurrence != null) {
            return "Recurrence{" +
                    "type='" + recurrence.getType() + '\'' +
                    ", interval=" + recurrence.getInterval() +
                    ", startDate='" + recurrence.getStartDate() + '\'' +
                    ", endDate='" + recurrence.getEndDate() + '\'' +
                    ", daysOfWeek=" + recurrence.getDaysOfWeek() +
                    '}';
        } else {
            return "No recurrence information provided";
        }
    }
}
