package com.jash.qswiki.entities;

import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.RadioGroup;

import com.google.gson.annotations.SerializedName;
import com.jash.qswiki.R;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jash
 * Date: 15-12-30
 * Time: 下午3:21
 */
public class ArticleItem implements RadioGroup.OnCheckedChangeListener {
    private String format;
    private String image;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @SerializedName("pic_url")
    private String pic;
    private int published_at;
    private String tag;
    private UserEntity user;
    private ImageSizeEntity image_size;
    private int id;
    private VotesEntity votes;
    private int created_at;
    private String content;
    private String state;
    private int comments_count;
    private boolean allow_comment;
    private int share_count;
    private String type;

    public void setFormat(String format) {
        this.format = format;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPublished_at(int published_at) {
        this.published_at = published_at;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setImage_size(ImageSizeEntity image_size) {
        this.image_size = image_size;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVotes(VotesEntity votes) {
        this.votes = votes;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public void setAllow_comment(boolean allow_comment) {
        this.allow_comment = allow_comment;
    }

    public void setShare_count(int share_count) {
        this.share_count = share_count;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public String getImage() {
        return image;
    }

    public int getPublished_at() {
        return published_at;
    }

    public String getTag() {
        return tag;
    }

    public UserEntity getUser() {
        return user;
    }

    public ImageSizeEntity getImage_size() {
        return image_size;
    }

    public int getId() {
        return id;
    }

    public VotesEntity getVotes() {
        return votes;
    }

    public int getCreated_at() {
        return created_at;
    }

    public String getContent() {
        return content;
    }

    public String getState() {
        return state;
    }

    public int getComments_count() {
        return comments_count;
    }

    public boolean isAllow_comment() {
        return allow_comment;
    }

    public int getShare_count() {
        return share_count;
    }

    public String getType() {
        return type;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.support:
                    setSupportSate(1);
                    break;
                case R.id.unsupport:
                    setSupportSate(2);
                    break;
            }
            View buttonView = group.findViewById(checkedId);
            if (buttonView != null) {
                ViewCompat.setScaleX(buttonView, 0.8f);
                ViewCompat.setScaleY(buttonView, 0.8f);
                ViewCompat.animate(buttonView).scaleX(1).scaleY(1).setDuration(300).start();
            }

    }

    public static class UserEntity {
        private int avatar_updated_at;
        private int last_visited_at;
        private int created_at;
        private String state;
        private String email;
        private String last_device;
        private String role;
        private String login;
        private int id;
        private String icon;

        public void setAvatar_updated_at(int avatar_updated_at) {
            this.avatar_updated_at = avatar_updated_at;
        }

        public void setLast_visited_at(int last_visited_at) {
            this.last_visited_at = last_visited_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setLast_device(String last_device) {
            this.last_device = last_device;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getAvatar_updated_at() {
            return avatar_updated_at;
        }

        public int getLast_visited_at() {
            return last_visited_at;
        }

        public int getCreated_at() {
            return created_at;
        }

        public String getState() {
            return state;
        }

        public String getEmail() {
            return email;
        }

        public String getLast_device() {
            return last_device;
        }

        public String getRole() {
            return role;
        }

        public String getLogin() {
            return login;
        }

        public int getId() {
            return id;
        }

        public String getIcon() {
            return icon;
        }
    }

    public static class ImageSizeEntity {
        private List<Integer> s;
        private List<Integer> m;

        public void setS(List<Integer> s) {
            this.s = s;
        }

        public void setM(List<Integer> m) {
            this.m = m;
        }

        public List<Integer> getS() {
            return s;
        }

        public List<Integer> getM() {
            return m;
        }
    }

    public static class VotesEntity {
        private int down;
        private int up;

        public void setDown(int down) {
            this.down = down;
        }

        public void setUp(int up) {
            this.up = up;
        }

        public int getDown() {
            return down;
        }

        public int getUp() {
            return up;
        }
    }
    private static final Pattern pattern = Pattern.compile("(\\d+)\\d{4}");
    private static final String IMAGE_URL = "http://pic.qiushibaike.com/system/pictures/%s/%s/%s/%s";
    private static final String ICON_URL = "http://pic.qiushibaike.com/system/avtnew/%s/%s/thumb/%s";
    public String getImageUrl(){
        if (image != null) {
            Matcher matcher = pattern.matcher(image);
            matcher.find();
            return String.format(IMAGE_URL, matcher.group(1), matcher.group(), "small", image);
        } else {
            return null;
        }
    }
    public String getIconUrl(){
        if (user != null) {
            return String.format(ICON_URL, user.id / 10000, user.id, user.icon);
        } else {
            return null;
        }
    }
    private int supportSate = 0;

    public int getSupportSate() {
        return supportSate;
    }

    public void setSupportSate(int supportSate) {
        this.supportSate = supportSate;
    }
}
