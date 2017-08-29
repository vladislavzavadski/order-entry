package com.netcracker.orderentry.catalog.controller;

import com.netcracker.orderentry.catalog.domain.Tag;
import com.netcracker.orderentry.catalog.service.TagService;
import com.netcracker.orderentry.catalog.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ulza1116 on 8/21/2017.
 */
@RestController
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @RequestMapping(value = "/tag", method = RequestMethod.POST)
    public Tag createTag(@RequestBody Tag tag){
        return tagService.createTag(tag);
    }

    @RequestMapping(value = "/tag/{tagId}", method = RequestMethod.GET)
    public Tag getTag(@PathVariable("tagId") int tagId) throws NotFoundException {
        return tagService.getTag(tagId);
    }

    @RequestMapping(value = "/tag/{tagId}", method = RequestMethod.PUT)
    public Tag updateTag(@RequestBody Tag tag, @PathVariable("tagId") int tagId) throws NotFoundException {
        return tagService.updateTag(tag, tagId);
    }

    @RequestMapping(value = "/tag/{tagId}", method = RequestMethod.DELETE)
    public void deleteTag(@PathVariable("tagId") int tagId) throws NotFoundException {
        tagService.deleteTag(tagId);
    }

    @RequestMapping(value = "/tag/list", method = RequestMethod.POST)
    public List<Tag> createTag(@RequestBody List<Tag> tags){
        return tagService.createTag(tags);
    }

}
