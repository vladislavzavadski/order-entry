package com.netcracker.orderentry.catalog.service.impl;

import com.netcracker.orderentry.catalog.domain.Tag;
import com.netcracker.orderentry.catalog.repository.TagRepository;
import com.netcracker.orderentry.catalog.service.TagService;
import com.netcracker.orderentry.catalog.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ulza1116 on 8/21/2017.
 */
@Service
public class DefaultTagService implements TagService {

    private final TagRepository tagRepository;

    @Autowired
    public DefaultTagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag createTag(Tag tag){
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(int tagId) throws NotFoundException {
        Tag tag = tagRepository.findOne(tagId);

        if(tag == null){
            throw new NotFoundException("Tag with id = " + tagId + " was not found");
        }

        return tagRepository.findOne(tagId);
    }

    @Override
    public void deleteTag(int tagId) throws NotFoundException {

        if(!tagRepository.exists(tagId)){
            throw new NotFoundException("Tag with id = " + tagId + " was not found");
        }

        tagRepository.delete(tagId);
    }

    @Override
    public Tag updateTag(Tag tag, int tagId) throws NotFoundException {
        Tag storedTag = tagRepository.findOne(tagId);

        if(!tagRepository.exists(tagId)){
            throw new NotFoundException("Tag with id = " + tagId + " was not found");
        }

        storedTag.setValue(tag.getValue());
        return tagRepository.save(storedTag);
    }

    @Override
    public List<Tag> createTag(List<Tag> tagList){
        return tagRepository.save(tagList);
    }
}
