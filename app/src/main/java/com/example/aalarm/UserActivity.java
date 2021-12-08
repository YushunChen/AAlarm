package com.example.aalarm;

public class UserActivity {
    private String name;
    private int frequency; //1-10
    private boolean happenMonday;
    private boolean happenTuesday;
    private boolean happenWednesday;
    private boolean happenThursday;
    private boolean happenFriday;
    private boolean happenSaturday;
    private boolean happenSunday;

    public UserActivity (String name, int frequency, boolean happenMonday, boolean happenTuesday,
                         boolean happenWednesday, boolean happenThursday, boolean happenFriday,
                         boolean happenSaturday,boolean happenSunday){
        this.name = name;
        this.frequency = frequency;
        this.happenMonday = happenMonday;
        this.happenTuesday = happenTuesday;
        this.happenWednesday = happenWednesday;
        this.happenThursday = happenThursday;
        this.happenFriday = happenFriday;
        this.happenSaturday = happenSaturday;
        this.happenSunday = happenSunday;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getFrequency(){
        return this.frequency;
    }

    public void setFrequency(int frequency){
        this.frequency = frequency;
    }

    public boolean getHappenMonday(){
        return this.happenMonday;
    }

    public void setHappenMonday(boolean happenMonday){
        this.happenMonday = happenMonday;
    }

    public boolean getHappenTuesday(){
        return this.happenTuesday;
    }

    public void setHappenTuesday(boolean happenTuesday){
        this.happenTuesday = happenTuesday;
    }

    public boolean getHappenWednesday(){
        return this.happenWednesday;
    }

    public void setHappenWednesday(boolean happenWednesday){
        this.happenWednesday = happenWednesday;
    }

    public boolean getHappenThursday(){
        return this.happenThursday;
    }

    public void setHappenThursday(boolean happenThursday){
        this.happenThursday = happenThursday;
    }

    public boolean getHappenFriday(){
        return this.happenFriday;
    }

    public void setHappenFriday(boolean happenFriday){
        this.happenFriday = happenFriday;
    }

    public boolean getHappenSaturday(){
        return this.happenSaturday;
    }

    public void setHappenSaturday(boolean happenSaturday){
        this.happenSaturday = happenSaturday;
    }

    public boolean getHappenSunday(){
        return this.happenSunday;
    }

    public void setHappenSunday(boolean happenSunday){
        this.happenSunday = happenSunday;
    }


}
