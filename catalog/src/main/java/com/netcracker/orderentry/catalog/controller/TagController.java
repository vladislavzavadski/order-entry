package com.netcracker.orderentry.catalog.controller;

import com.netcracker.orderentry.catalog.domain.Tag;
import com.netcracker.orderentry.catalog.service.TagService;
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
    public void createTag(@RequestBody Tag tag){
        tagService.createTag(tag);
    }

    @RequestMapping(value = "/tag/{tagId}", method = RequestMethod.GET)
    public Tag getTag(@PathVariable("tagId") int tagId){
        return tagService.getTag(tagId);
    }

    @RequestMapping(value = "/tag/{tagId}", method = RequestMethod.PUT)
    public void updateTag(@RequestBody Tag tag, @PathVariable("tagId") int tagId) {
        tagService.updateTag(tag, tagId);
    }

    @RequestMapping(value = "/tag/{tagId}", method = RequestMethod.DELETE)
    public void deleteTag(@PathVariable("tagId") int tagId){
        tagService.deleteTag(tagId);
    }

    @RequestMapping(value = "/tag/list", method = RequestMethod.POST)
    public void createTag(@RequestBody List<Tag> tags){
        tagService.createTag(tags);
    }

}
