package com.haotang.easyshare.mvp.model.entity.res;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/28 17:57
 */
public class CommentBean {
    private int total;
    private List<Comment> comments;
    private boolean isAddImgDev;
    private boolean isAddTagDev;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public boolean isAddImgDev() {
        return isAddImgDev;
    }

    public void setAddImgDev(boolean addImgDev) {
        isAddImgDev = addImgDev;
    }

    public boolean isAddTagDev() {
        return isAddTagDev;
    }

    public void setAddTagDev(boolean addTagDev) {
        isAddTagDev = addTagDev;
    }

    public CommentBean() {
    }

    public CommentBean(int total, List<Comment> comments, boolean isAddImgDev, boolean isAddTagDev) {
        this.total = total;
        this.comments = comments;
        this.isAddImgDev = isAddImgDev;
        this.isAddTagDev = isAddTagDev;
    }

    public static class Comment {
        private List<String> tags;
        private List<String> media;
        private String headImg;
        private String createTime;
        private String userName;
        private String content;
        private List<CommentTag> tagList;
        private List<CommentImg> mediaList;

        public Comment(String headImg, String createTime, String userName, String content, List<CommentTag> tagList, List<CommentImg> mediaList) {
            this.headImg = headImg;
            this.createTime = createTime;
            this.userName = userName;
            this.content = content;
            this.tagList = tagList;
            this.mediaList = mediaList;
        }

        public Comment(List<String> tags, List<String> media, String headImg, String createTime, String userName, String content) {
            this.tags = tags;
            this.media = media;
            this.headImg = headImg;
            this.createTime = createTime;
            this.userName = userName;
            this.content = content;
        }

        public Comment() {
        }

        public List<CommentTag> getTagList() {
            return tagList;
        }

        public void setTagList(List<CommentTag> tagList) {
            this.tagList = tagList;
        }

        public List<CommentImg> getMediaList() {
            return mediaList;
        }

        public void setMediaList(List<CommentImg> mediaList) {
            this.mediaList = mediaList;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public List<String> getMedia() {
            return media;
        }

        public void setMedia(List<String> media) {
            this.media = media;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
