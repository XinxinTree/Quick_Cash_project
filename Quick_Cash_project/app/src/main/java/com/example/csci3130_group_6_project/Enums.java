package com.example.csci3130_group_6_project;

public class Enums {
    public enum jobTypes {
        PCRepair {
            public String toString() {
                return "Repairing a computer";
            }
        },
        lawnMowing {
            public String toString() {
                return "Mowing the lawn";
            }
        },
        dogWalking {
            public String toString() {
                return "Walking a dog";
            }
        },
        babysitting {
            public String toString() {
                return "Baby Sitting";
            }
        },
        groceryPickUp {
            public String toString() {
                return "Picking up a grocery";
            }
        }
    }

    public enum urgency {
        Urgent {
            public String toString() {
                return "Urgent";
            }
        },
        High {
            public String toString() {
                return "High";
            }
        },
        Normal {
            public String toString() {
                return "Normal";
            }
        },
        Low{
            public String toString() {
                return "Low";
            }
        }
    }
}
