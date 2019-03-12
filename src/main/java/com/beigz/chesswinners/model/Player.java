package com.beigz.chesswinners.model;

/**
 * Created by Zaheer Beig on 05/03/2018.
 */
public class Player {

    private Integer rank;
    private String serialNo;
    private String name;
    private String gender;
    private Integer rating;
    private String club;
    private String type;
    private String prizeMoney;
    private String winningCategory;
    private String points;
    private String disability;
    private Integer age;

    public Player(Integer rank, String serialNo, String name, String gender, Integer rating, String club, String type, String prizeMoney, String winningCategory, String points, String disability, Integer age) {
        this.rank = rank;
        this.serialNo = serialNo;
        this.name = name;
        this.gender = gender;
        this.rating = rating;
        this.club = club;
        this.type = type;
        this.prizeMoney = prizeMoney;
        this.winningCategory = winningCategory;
        this.points = points;
        this.disability = disability;
        this.age = age;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrizeMoney() {
        return prizeMoney;
    }

    public void setPrizeMoney(String prizeMoney) {
        this.prizeMoney = prizeMoney;
    }

    public String getWinningCategory() {
        return winningCategory;
    }

    public void setWinningCategory(String winningCategory) {
        this.winningCategory = winningCategory;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Player{" +
                "rank=" + rank +
                ", serialNo='" + serialNo + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", rating=" + rating +
                ", club='" + club + '\'' +
                ", type='" + type + '\'' +
                ", prizeMoney='" + prizeMoney + '\'' +
                ", winningCategory='" + winningCategory + '\'' +
                ", points='" + points + '\'' +
                ", disability='" + disability + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
