public class Comment {
    private String description;
    private final String profile;

    public Comment(String description, String profile) {
        this.description = description;
        this.profile = profile;
    }

    public String getProfile() {
        return profile;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Comment {" +
                "\n\tdescription='" + description + '\'' +
                ", \n\tprofile=" + profile +
                "\n\t}";
    }
}
