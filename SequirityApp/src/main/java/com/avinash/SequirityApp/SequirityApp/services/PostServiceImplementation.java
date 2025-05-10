package com.avinash.SequirityApp.SequirityApp.services;


import com.avinash.SequirityApp.SequirityApp.dto.PostDto;
import com.avinash.SequirityApp.SequirityApp.entities.PostEntity;
import com.avinash.SequirityApp.SequirityApp.entities.User;
import com.avinash.SequirityApp.SequirityApp.exception.ResourceNotFoundException;
import com.avinash.SequirityApp.SequirityApp.repositories.PostRepositories;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImplementation implements PostService {


    private final PostRepositories postRepositories;
    private final ModelMapper modelMapper;

    @Override
    public List<PostDto> getAllPosts()
    {
        return postRepositories.findAll().
                stream().
                map(postEntity -> modelMapper.map(
                        postEntity,PostDto.class)).
                collect(Collectors.toList());
    }

    @Override
    public PostDto createNewPost(PostDto inputPost) {

        PostEntity postEntity = modelMapper.map(inputPost,PostEntity.class);

        return modelMapper.map(postRepositories.save(postEntity),PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto inputPost,Long postId) {
//        PostEntity olderPost = postRepositories.findById(postId).orElseThrow( ()-> new RuntimeException("RPost notfound " + postId));
//        inputPost.setId((postId));
//        modelMapper.getConfiguration()
//                .setPropertyCondition(context -> context.getSource() != null || context.getSource() == null);
//        System.out.println("=======input post========");
//        System.out.println(inputPost);
//        System.out.println("==========================");
//        modelMapper.map(inputPost, olderPost);
//
//        System.out.println("=======older post========");
//        System.out.println(olderPost);
//        System.out.println("==========================");
//
//        PostEntity savedPost = postRepositories.save(olderPost);
//        return modelMapper.map(savedPost,PostDto.class);


        Field[] fields = inputPost.getClass().getDeclaredFields();
        PostEntity olderPost = postRepositories.findById(postId).orElseThrow(() -> new RuntimeException("RPost notfound " + postId));
//
//           for (Field field : fields) {
//               field.setAccessible(true); // Make private fields accessible
//               try {
////                   Object value = field.get(inputPost);
////                   if (value != null) { // Check if the field in the source is non-null
////                       field.set(olderPost, value); // Update the target object with the source value
////                   }
//
//               }
//

        Field[] sourceFields = inputPost.getClass().getDeclaredFields();
        Field[] targetFields = olderPost.getClass().getDeclaredFields();
        for (Field sourceField : sourceFields) {
            sourceField.setAccessible(true);
            try {
                Object value = sourceField.get(inputPost); // Get the value from inputPost
                if (value != null) { // Only update if the value is non-null
                    for (Field targetField : targetFields) {
                        if (targetField.getName().equals(sourceField.getName())
                                && targetField.getType().isAssignableFrom(sourceField.getType())) {
                            targetField.setAccessible(true);
                            targetField.set(olderPost, value); // Set the value to olderPost
                            break;
                        }
                    }
                }
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to access field: " + sourceField.getName(), e);
            }

        }
        PostEntity savedPost = postRepositories.save(olderPost);
        return modelMapper.map(savedPost, PostDto.class);


    }

    @Override
    public PostDto getPostById(Long postId) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();    //// used to get user-details as we set it in JwtService =>    new UsernamePasswordAuthenticationToken(user,null,null);
log.info("=====================================================================");
        log.info("user : {} ",user);
log.info("=====================================================================");

        PostEntity post = postRepositories.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post with given postId : " + postId + " not exists"));

        return modelMapper.map(post,PostDto.class);
    }

}


