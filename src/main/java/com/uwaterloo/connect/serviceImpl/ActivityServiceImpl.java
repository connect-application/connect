package com.uwaterloo.connect.serviceImpl;

import com.uwaterloo.connect.model.Activity;
import com.uwaterloo.connect.model.ActivityRequest;
import com.uwaterloo.connect.model.Attachment;
import com.uwaterloo.connect.model.Post;
import com.uwaterloo.connect.repository.ActivityRepository;
import com.uwaterloo.connect.repository.AttachmentRepository;
import com.uwaterloo.connect.repository.PostRepository;
import com.uwaterloo.connect.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public String createActivity(ActivityRequest activityRequest){
        try{

            //New post
            Integer postId = createPostForActivity(activityRequest);
            //Save attachments
            createAttachmentsforPost(postId, activityRequest.getFiles());
            System.out.println("act: "+postId);
            //Make activity for the new post
            Activity activity = new Activity();
            activity.setPostId(postId);
            activity.setCategoryId(activityRequest.getCategoryId());
            activity.setStatusId(activityRequest.getStatusId());
            activity.setRecurring(activityRequest.isRecurring());
            // Save dateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            activity.setStartTime(LocalDateTime.parse(activityRequest.getStartTime(), formatter));
            activity.setEndTime(LocalDateTime.parse(activityRequest.getEndTime(), formatter));
            //Save Activity
            activityRepository.save(activity);

            return SUCCESS;
        }catch (Exception e){
            return ERROR+e;
        }
    }
    public Integer createPostForActivity(ActivityRequest activityRequest){
        try{
            Post post = new Post(activityRequest.getUserId(), activityRequest.getPostText(), activityRequest.isShared());
            postRepository.save(post);
            System.out.println(post.getPostId());
            return post.getPostId();
        }catch(Exception e){
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
            activityRepository.delete(activity);
            return SUCCESS;
        }catch (Exception e){
            return ERROR+e;
        }
    }

}
