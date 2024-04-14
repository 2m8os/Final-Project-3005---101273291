package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;


public class Main {
    public static void main(String[] args)
    {
        //use your own url, user and password
        String url = "jdbc:postgresql://localhost:5432/Testing - Final";
        String user = "postgres";
        String password = "4125Ltjt";

        //scanner used for user input
        Scanner in = new Scanner(System.in);

        try
        {
            Class.forName("org.postgresql.Driver");
            // Connect to the database
            Connection con = DriverManager.getConnection(url, user, password);
            if (con != null) {
                System.out.println("Connected to PostgreSQL successfully!");
            } else {
                System.out.println("Failed to establish connection.");
            }

            //holds user response
            int res;
            do
            {
                System.out.println("\nPlease enter your selection: \n");
                System.out.print("\nWho are you logging in as\n");
                System.out.println("[1] - Member");
                System.out.println("[2] - Trainer");
                System.out.println("[3] - Admin");
                System.out.println("[0] - Exit");

                res = in.nextInt();
                in.nextLine();

                if (res == 0) {break;}//exit

                // Checks that the response is valid
                while (res < 0 || res > 3) {
                    System.out.println("Invalid Selection: ");
                    in.nextLine();
                    res = in.nextInt();
                }
                // Goes to correct user functions, depending on res
                if (res == 1)
                {
                    //Log in/registration for a member
                    memberLogin(in,con);
                }
                else if (res == 2)
                {
                    // Trainer actions
                    trainerLogin(in,con);
                }
                else if (res == 3)
                {
                    //Admin actions
                    adminActions(in,con);
                }
            }
            while (res != 0);

            con.close();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    // MEMBER FUNCTIONS_____________________________________________
    //Gives user(member) choice to login or register as a new member
    public static void memberLogin(Scanner in, Connection con)
    {
        int res; //User response
        do
        {
            System.out.println("\nPlease enter your selection: \n");
            System.out.println("[1] - Login");
            System.out.println("[2] - Register");
            System.out.println("[0] - Exit");
            res = in.nextInt();
            in.nextLine();
            if (res == 0) {break;}//exit
            // Checks that the response is valid
            while (res < 0 || res > 2) {
                System.out.println("Invalid Selection: ");
                in.nextLine();
                res = in.nextInt();
            }
            // Calls the right function based of response
            if (res == 1)
            {
                // Move onto other member actions
                // Get member id from user
                System.out.println("\nPlease enter your MemberID: \n");
                res = in.nextInt();
                in.nextLine();
                memberActions(in,con,res);
            }
            else if (res == 2)
            {
                //Move to registration view
                memberRegistration(in,con);
            }
        }
        while (res != 0);
    }

    public static void memberRegistration(Scanner in, Connection con)
    {
        String first,last,email,sex,tdate;
        int height,weight,age,tweight;
        System.out.println("Please enter your first name: ");
        first = in.nextLine();
        System.out.println("Please enter your last name: ");
        last = in.nextLine();
        System.out.println("Please enter your email address: ");
        email = in.nextLine();
        System.out.println("Please enter your sex: ");
        sex = in.nextLine();
        System.out.println("Please enter your height in centimeters: ");
        height = in.nextInt();
        in.nextLine();
        System.out.println("Please enter your weight in pounds: ");
        weight = in.nextInt();
        in.nextLine();
        System.out.println("Please enter your age: ");
        age = in.nextInt();
        System.out.println("Please enter your target weight: ");
        tweight = in.nextInt();
        in.nextLine();
        System.out.println("Please enter your the date you wish to achieve this weight: ");
        System.out.println("Please use the format YYYY-MM-DD");
        tdate = in.nextLine();
        int tempOut = -1;

        in.nextLine();
        //tdate = "2021-10-10";
        try{
            Class.forName("org.postgresql.Driver");
            Statement statement = con.createStatement();

            // Add new member to members table
            statement.executeUpdate("INSERT INTO Members (fname,lname,email, weight, target_weight,target_date,age,height,sex) VALUES('" +
                    first + "', '" + last + "', '" + email + "', " + weight + ", "+ tweight + ", '" + tdate + "', " + age + ", " + height + ", '" + sex + "');");

            System.out.println("Welcome!");

            // Give the user their member ID
            statement.executeQuery("SELECT member_id FROM Members WHERE email='"+ email + "';");
            ResultSet resultset = statement.getResultSet();
            while (resultset.next()){
                tempOut = resultset.getInt("member_id");
            }

            System.out.println("Your member ID is: " + tempOut);

            statement.close();

        }catch (Exception e) {
            e.printStackTrace();
        }

        memberActions(in,con,tempOut);
    }

    public static void memberActions(Scanner in, Connection con, int id)
    {
        int res; //User response
        do
        {
            System.out.println("\nPlease enter your selection: \n");
            System.out.println("[1] - View Dashboard");
            System.out.println("[2] - Update Profile");
            System.out.println("[3] - Manage Schedule");
            System.out.println("[0] - Exit");
            res = in.nextInt();
            in.nextLine();
            if (res == 0) {break;}//exit
            // Checks that the response is valid
            while (res < 0 || res > 3) {
                System.out.println("Invalid Selection: ");
                in.nextLine();
                res = in.nextInt();
            }
            // Calls the right function based of response
            if (res == 1)
            {
                // Look at dashboard
                memberDashboard(in,con,id);
            }
            else if (res == 2) {
                //Update Profile
                int option;
                do {
                    System.out.println("\nWhat would you like to update: \n");
                    System.out.println("[1] - Personal information and health metrics");
                    System.out.println("[2] - Fitness goals");
                    System.out.println("[0] - Exit");
                    option = in.nextInt();
                    in.nextLine();
                    if (option == 0) {
                        break;
                    }
                    //exit
                    // Checks that the response is valid
                    while (option < 0 || option > 2) {
                        System.out.println("Invalid Selection: ");
                        in.nextLine();
                        res = in.nextInt();
                    }
                    memberUpdateProfile(in,con,id,option);
                } while (option != 0);
            }
            else if (res == 3)
            {
                //Update Profile
                int option ;
                do {
                    System.out.println("\nHow would you like to change your schedule: \n");
                    System.out.println("[1] - Remove a class");
                    System.out.println("[2] - Schedule a class");
                    System.out.println("[0] - Exit");
                    option = in.nextInt();
                    in.nextLine();
                    if (option == 0) {
                        break;
                    }
                    ; //exit
                    // Checks that the response is valid
                    while (option < 0 || option > 2) {
                        System.out.println("Invalid Selection: ");
                        in.nextLine();
                        res = in.nextInt();
                    }
                    memberUpdateSchedule(in,con,id,option);
                } while (option != 0);
            }
        }
        while (res != 0);
    }

    public static void memberDashboard(Scanner in, Connection con, int res)
    {
        int weight = 0; //weight of user

        // prints Current classes/exercise routines
        try{
            Class.forName("org.postgresql.Driver");
            Statement statement = con.createStatement();
            statement.executeQuery("SELECT class_id, class_desc, time_slot, room_num FROM Classes NATURAL JOIN Class_Members WHERE member_id = " + res + " ORDER BY class_id DESC;");
            ResultSet resultSet = statement.getResultSet();
            System.out.println("Classes/Exercise Routines: ");
            while (resultSet.next()){
                System.out.print("Class ID: ");
                System.out.println(resultSet.getInt("class_id"));
                System.out.print("Class Description: ");
                System.out.println(resultSet.getString("class_desc"));
                System.out.print("Class Time Slot: ");
                System.out.println(resultSet.getString("time_slot"));
                System.out.print("Room Number: ");
                System.out.println(resultSet.getInt("room_num"));
                System.out.println("\n");
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        // Print health statistics
        try{
            Class.forName("org.postgresql.Driver");
            Statement statement = con.createStatement();
            statement.executeQuery("SELECT fname, lname, age, height, weight, sex FROM Members WHERE member_id =  " + res + ";");
            ResultSet resultSet = statement.getResultSet();
            System.out.println("Health Statistics: ");
            while (resultSet.next()){
                System.out.print("Name: ");
                System.out.print(resultSet.getString("fname"));
                System.out.print(" ");
                System.out.println(resultSet.getString("lname"));
                System.out.print("Age: ");
                System.out.println(resultSet.getInt("age"));
                System.out.print("Height(cm): ");
                System.out.println(resultSet.getInt("height"));
                System.out.print("Weight(lbs): ");
                System.out.println(resultSet.getInt("weight"));
                weight = resultSet.getInt("weight");
                System.out.print("Sex: ");
                System.out.println(resultSet.getString("sex"));
                System.out.println("\n");
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        // Print fitness achievements
        int tweight = 0; //target weight
        try{
            Class.forName("org.postgresql.Driver");
            Statement statement = con.createStatement();
            statement.executeQuery("SELECT target_weight, target_date FROM Members WHERE member_id =  " + res + ";");
            ResultSet resultSet = statement.getResultSet();
            System.out.println("Fitness achievement: ");
            System.out.println("Weight: " + weight);
            while (resultSet.next()){
                System.out.print("Your Target Weight(lbs) is : ");
                System.out.println(resultSet.getInt("target_weight"));
                tweight = resultSet.getInt("target_weight");
                System.out.print("Your Target Date Is: ");
                System.out.println(resultSet.getDate("target_date"));
                System.out.println("\n");
            }

            if (weight > tweight)
            {
                System.out.println("You're over your target weight!");
            }
            else if (weight <= tweight)
            {
                System.out.println("You're under your target weight!");
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void memberUpdateProfile(Scanner in,Connection con,int id, int option)
    {
        String first,last,email,tdate;
        int height,weight,age,tweight;
        try {
            Class.forName("org.postgresql.Driver");
            Statement statement = con.createStatement();
            if (option == 1) {
                System.out.println("Please enter your new first name: ");
                first = in.nextLine();
                System.out.println("Please enter your new last name: ");
                last = in.nextLine();
                System.out.println("Please enter your new email address: ");
                email = in.nextLine();
                System.out.println("Please enter your new height in centimeters: ");
                height = in.nextInt();
                in.nextLine();
                System.out.println("Please enter your new weight in pounds: ");
                weight = in.nextInt();
                in.nextLine();
                System.out.println("Please enter your new age: ");
                age = in.nextInt();
                statement.executeUpdate("UPDATE Members SET fname = '" + first + "', lname = '" + last + "', email = '" + email +"', height = " + height + ", weight = " + weight + ", age = " + age + " WHERE member_id = " + id + ";");
            }
            else if (option == 2)
            {
                System.out.println("Please enter your new target weight: ");
                tweight = in.nextInt();
                in.nextLine();
                System.out.println("Please enter your the date you wish to achieve this weight: ");
                System.out.println("Please use the format YYYY-MM-DD");
                tdate = in.nextLine();
                statement.executeUpdate("UPDATE Members SET target_date = '" + tdate + "', target_weight = " + tweight + " WHERE member_id = " + id + ";");
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void memberUpdateSchedule(Scanner in,Connection con,int id, int option)
    {
        int cid;
        try {
            Class.forName("org.postgresql.Driver");
            Statement statement = con.createStatement();

            // Print classes currently enrolled in
            statement.executeQuery("SELECT class_id, class_desc, time_slot, room_num FROM Classes NATURAL JOIN Class_Members WHERE member_id = " + id + " ORDER BY class_id DESC;");
            ResultSet resultSet = statement.getResultSet();
            System.out.println("Your Classes/Exercise Routines: ");
            while (resultSet.next()){
                System.out.print("Class ID: ");
                System.out.println(resultSet.getInt("class_id"));
                System.out.print("Class Description: ");
                System.out.println(resultSet.getString("class_desc"));
                System.out.print("Class Time Slot: ");
                System.out.println(resultSet.getString("time_slot"));
                System.out.print("Room Number: ");
                System.out.println(resultSet.getInt("room_num"));
                System.out.println("\n");
            }

            // print available classes
            System.out.println("All available Classes: ");
            statement.executeQuery("SELECT class_id, class_desc, time_slot, room_num, spots_left FROM Classes WHERE spots_left > 0;");
            ResultSet results = statement.getResultSet();
            while (results.next())
            {
                System.out.print("Class ID: ");
                System.out.println(results.getInt("class_id"));
                System.out.print("Class Description: ");
                System.out.println(results.getString("class_desc"));
                System.out.print("Class Time Slot: ");
                System.out.println(results.getString("time_slot"));
                System.out.print("Room Number: ");
                System.out.println(results.getInt("room_num"));
                System.out.print("Spots left: ");
                System.out.println(results.getInt("spots_left"));
                System.out.println("\n");
            }

            // Get class to be changed
            System.out.println("Please select a class: ");
            cid = in.nextInt();
            in.nextLine();

            //get spots left
            int spots = 0;
            statement.executeQuery("SELECT spots_left FROM Classes WHERE class_id = " + cid + ";");
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {spots = rs.getInt("spots_left");}

            //get amount owed from members
            int amount_owed = 0;
            statement.executeQuery("SELECT amount_owed FROM Members WHERE member_id = " + id + ";");
            ResultSet rt = statement.getResultSet();
            while (rt.next()) {amount_owed = rt.getInt("amount_owed");}

            if (option == 1) {
                // Remove from tables
                statement.executeUpdate("DELETE FROM Class_members WHERE member_id = " + id + " AND class_id = " + cid + ";");
                // update spots left ++
                statement.executeUpdate("UPDATE Classes SET spots_left = " + (spots + 1) + " WHERE class_id = " + cid + ";");
                // update amount owed --
                statement.executeUpdate("UPDATE Members SET amount_owed = " + (amount_owed - 10) + " WHERE member_id = " + id + ";");
            }
            else if (option == 2)
            {
                // Insert new class member
                statement.executeUpdate("INSERT INTO Class_Members (class_id, member_id) VALUES (" + cid + ", " + id + ");");
                // update slots left --
                statement.executeUpdate("UPDATE Classes SET spots_left = " + (spots - 1) + " WHERE class_id = " + cid + ";");
                // update spots left ++
                statement.executeUpdate("UPDATE Members SET amount_owed = " + (amount_owed - 10) + " WHERE member_id = " + id + ";");
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // TRAINER FUNCTIONS_____________________________________________
    public static void trainerLogin(Scanner in, Connection con)
    {
        int res; //User response
        int tid = -1;

        System.out.println("\nPlease enter your Trainer ID: \n");
        tid = in.nextInt();
        in.nextLine();

        do
        {
            System.out.println("\nPlease enter your selection: \n");
            System.out.println("[1] - Check Member Profile");
            System.out.println("[2] - Manage Schedule");
            System.out.println("[0] - Exit");
            res = in.nextInt();
            in.nextLine();
            if (res == 0) {break;}; //exit
            // Checks that the response is valid
            while (res < 0 || res > 2) {
                System.out.println("Invalid Selection: ");
                in.nextLine();
                res = in.nextInt();
            }

            // Calls the right function based of response
            if (res == 1)
            {
                // Move onto check member
                trainerCheckMember(in,con,tid);
            }
            else if (res == 2)
            {
                //Move to registration view
                trainerUpdateSchedule(in,con,tid);
            }
        }
        while (res != 0);
    }

    public static void trainerCheckMember(Scanner in, Connection con, int tid)
    {
        String fname;
        String lname;

        try {
            Class.forName("org.postgresql.Driver");
            Statement statement = con.createStatement();

            System.out.println("Which member profile would you like to view?");
            System.out.println("Please enter their first name:");
            fname = in.nextLine();
            System.out.println("Please enter their last name:");
            lname = in.nextLine();

            // Print classes currently enrolled in
            statement.executeQuery("SELECT fname, lname, age, height, weight, sex, target_date, target_weight FROM Members WHERE fname = '"+ fname +"' AND lname = '" + lname +  "';");
            ResultSet resultSet = statement.getResultSet();
            System.out.println(fname + " " + lname + "'s profile: ");
            while (resultSet.next()){
                System.out.print("Name: ");
                System.out.print(resultSet.getString("fname"));
                System.out.print(" ");
                System.out.println(resultSet.getString("lname"));
                System.out.print("Age: ");
                System.out.println(resultSet.getInt("age"));
                System.out.print("Height(cm): ");
                System.out.println(resultSet.getInt("height"));
                System.out.print("Weight(lbs): ");
                System.out.println(resultSet.getInt("weight"));
                System.out.print("Sex: ");
                System.out.println(resultSet.getString("sex"));
                System.out.print("Target Weight(lbs) : ");
                System.out.println(resultSet.getInt("target_weight"));
                System.out.print("Target Date: ");
                System.out.println(resultSet.getDate("target_date"));
                System.out.println("\n");
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void trainerUpdateSchedule(Scanner in, Connection con, int tid)
    {
        String day;
        String status;

        try {
            Class.forName("org.postgresql.Driver");
            Statement statement = con.createStatement();
            // Print availabilty of trainer
            statement.executeQuery("SELECT mon_status, tue_status, wen_status, thu_status, fri_status FROM Trainers WHERE trainer_id = " + tid + ";");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()){
                System.out.print("Monday Availability: ");
                System.out.println(resultSet.getString("mon_status"));
                System.out.print("Tuesday Availability: ");
                System.out.println(resultSet.getString("tue_status"));
                System.out.print("Wednesday Availability: ");
                System.out.println(resultSet.getString("wen_status"));
                System.out.print("Thursday Availability: ");
                System.out.println(resultSet.getString("thu_status"));
                System.out.print("Friday Availability: ");
                System.out.println(resultSet.getString("fri_status"));
                System.out.println("\n");
            }

            System.out.println("Which day's availability would you like to change?");
            System.out.println("Please enter the day:");
            day = in.nextLine();
            System.out.println("Please enter the new availability:");
            status = in.nextLine();

            if (day.equalsIgnoreCase("monday")) {day = "mon_status";}
            else if (day.equalsIgnoreCase("tuesday")) {day = "tue_status";}
            else if (day.equalsIgnoreCase("wednesday")) {day = "wen_status";}
            else if (day.equalsIgnoreCase("thursday")) {day = "thu_status";}
            else if (day.equalsIgnoreCase("friday")) {day = "fri_status";}

            //update to new availability
            statement.executeUpdate("UPDATE Trainers SET " + day + "= '" + status + "' WHERE trainer_id = " + tid + ";");
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //ADMIN FUNCTIONS
    public static void adminActions(Scanner in, Connection con)
    {
        int res; //User response
        do
        {
            System.out.println("\nPlease enter your selection: \n");
            System.out.println("[1] - Monitor Equipment");
            System.out.println("[2] - Manage Rooms");
            System.out.println("[3] - Update Class Schedule");
            System.out.println("[4] - Billing And Payment");
            System.out.println("[0] - Exit");
            res = in.nextInt();
            in.nextLine();
            if (res == 0) {break;}; //exit
            // Checks that the response is valid
            while (res < 0 || res > 4) {
                System.out.println("Invalid Selection: ");
                in.nextLine();
                res = in.nextInt();
            }
            // Calls the right function based of response
            if (res == 1)
            {
                // Look at equipment table
                adminCheckEquipment(in,con);
            }
            else if (res == 2) {
                //Manage Room
                int option = -1;
                do {
                    System.out.println("\nWhat would you like to do: \n");
                    System.out.println("[1] - Update Rooms");
                    System.out.println("[2] - View Rooms");
                    System.out.println("[0] - Exit");
                    option = in.nextInt();
                    in.nextLine();
                    if (option == 0) {
                        break;
                    }
                    ; //exit
                    // Checks that the response is valid
                    while (option < 0 || option > 2) {
                        System.out.println("Invalid Selection: ");
                        in.nextLine();
                        res = in.nextInt();
                    }
                    adminManageRooms(in,con,option);
                } while (option != 0);
            }
            else if (res == 3)
            {
                //Update classes
                int option = -1;
                do {
                    System.out.println("\nHow would you like to change your schedule: \n");
                    System.out.println("[1] - Remove a class");
                    System.out.println("[2] - Add a class");
                    System.out.println("[3] - Update a class");
                    System.out.println("[0] - Exit");
                    option = in.nextInt();
                    in.nextLine();
                    if (option == 0) {
                        break;
                    }
                    // Checks that the response is valid
                    while (option < 0 || option > 3) {
                        System.out.println("Invalid Selection: ");
                        in.nextLine();
                        res = in.nextInt();
                    }
                    adminUpdateClass(in,con,option);
                } while (option != 0);
            }

            else if (res == 4)
            {
                //Billing and Payment
                adminSendBill(in, con);
            }
        }
        while (res != 0);
    }

    public static void adminCheckEquipment(Scanner in, Connection con)
    {
        try{
            Class.forName("org.postgresql.Driver");
            Statement statement = con.createStatement();
            statement.executeQuery("SELECT * FROM Equipment");
            ResultSet resultSet = statement.getResultSet();
            System.out.println("List of Equipment: ");
            while (resultSet.next()){
                System.out.print("Equipment ID: ");
                System.out.println(resultSet.getInt("equipement_id"));
                System.out.print("Equipment Description: ");
                System.out.println(resultSet.getString("e_desc"));
                System.out.print("Condition: ");
                System.out.println(resultSet.getString("condition"));
                System.out.print("Amount: ");
                System.out.println(resultSet.getInt("amount"));
                System.out.print("Date Purchased: ");
                System.out.println(resultSet.getDate("purchase_date"));
                System.out.println("\n");
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void adminUpdateClass(Scanner in,Connection con,int option)
    {
        try{
            Class.forName("org.postgresql.Driver");
            Statement statement = con.createStatement();
            //View Rooms
            statement.executeQuery("SELECT * FROM Rooms;");
            ResultSet resultSet = statement.getResultSet();
            System.out.println("Rooms: ");
            while (resultSet.next()){
                System.out.print("Room Number: ");
                System.out.println(resultSet.getString("room_num"));
                System.out.print("Room Description: ");
                System.out.println(resultSet.getString("room_desc"));
                System.out.print("Monday Availability: ");
                System.out.println(resultSet.getString("mon_status"));
                System.out.print("Tuesday Availability: ");
                System.out.println(resultSet.getString("tue_status"));
                System.out.print("Wednesday Availability: ");
                System.out.println(resultSet.getString("wen_status"));
                System.out.print("Thursday Availability: ");
                System.out.println(resultSet.getString("thu_status"));
                System.out.print("Friday Availability: ");
                System.out.println(resultSet.getString("fri_status"));
                System.out.println("\n");
            }
            //View Trainers
            statement.executeQuery("SELECT * FROM Trainers;");
            ResultSet rs = statement.getResultSet();
            System.out.println("Trainers: ");
            while (rs.next()){
                System.out.print("Trainer ID: ");
                System.out.println(rs.getString("trainer_id"));
                System.out.print("Name: ");
                System.out.print(rs.getString("fname"));
                System.out.print(" ");
                System.out.println(rs.getString("lname"));
                System.out.print("Monday Availability: ");
                System.out.println(rs.getString("mon_status"));
                System.out.print("Tuesday Availability: ");
                System.out.println(rs.getString("tue_status"));
                System.out.print("Wednesday Availability: ");
                System.out.println(rs.getString("wen_status"));
                System.out.print("Thursday Availability: ");
                System.out.println(rs.getString("thu_status"));
                System.out.print("Friday Availability: ");
                System.out.println(rs.getString("fri_status"));
                System.out.println("\n");
            }

            // View Classes
            System.out.println("All Classes: ");
            statement.executeQuery("SELECT * FROM Classes NATURAL JOIN Trainers;");
            ResultSet results = statement.getResultSet();
            while (results.next())
            {
                System.out.print("Class ID: ");
                System.out.println(results.getInt("class_id"));
                System.out.print("Class Description: ");
                System.out.println(results.getString("class_desc"));
                System.out.print("Class Time Slot: ");
                System.out.println(results.getString("time_slot"));
                System.out.print("Room Number: ");
                System.out.println(results.getInt("room_num"));
                System.out.print("Spots left: ");
                System.out.println(results.getInt("spots_left"));
                System.out.print("Trainer in Charge: ");
                System.out.print(results.getString("fname"));
                System.out.print(" ");
                System.out.println(results.getString("lname"));
                System.out.print("Trainer ID: ");
                System.out.print(results.getInt("trainer_id"));
                System.out.println("\n");
            }

            if (option == 1)
            {
                adminDeleteClass(in,con);
            }
            // add class
            else if (option == 2)
            {
                adminAddClass(in,con);
            }
            // update class
            else if (option == 3)
            {
                adminUpdateClass(in,con);
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void adminAddClass(Scanner in, Connection con)
    {
        int rid, tid, spots;
        String desc, time;
        System.out.println("Please enter class description: ");
        desc = in.nextLine();
        System.out.println("Please enter date of class (Monday, Tuesday, ... , Friday): ");
        time = in.nextLine();
        System.out.println("Please enter the trainer (ID) you would like to assign to the class: ");
        tid = in.nextInt();
        System.out.println("Please enter the room the class will take place in: ");
        rid = in.nextInt();
        System.out.println("Please enter the max class size: ");
        spots = in.nextInt();
        int tempOut = -1;
        try{
            Class.forName("org.postgresql.Driver");
            Statement statement = con.createStatement();

            // Add new class
            statement.executeUpdate("INSERT INTO Classes (class_desc,time_slot,trainer_id, room_num, spots_left) VALUES('" +
                    desc + "', '" + time + "', " + tid + ", "+ rid + ", '" + spots + "');");

            // Find day to update
            if (time.equalsIgnoreCase("monday")) {time = "mon_status";}
            else if (time.equalsIgnoreCase("tuesday")) {time = "tue_status";}
            else if (time.equalsIgnoreCase("wednesday")) {time = "wen_status";}
            else if (time.equalsIgnoreCase("thursday")) {time = "thu_status";}
            else if (time.equalsIgnoreCase("friday")) {time = "fri_status";}

            // Update schedule of trainer and room
            statement.executeUpdate("UPDATE Rooms SET " + time + "= 'Booked' WHERE room_num = " + rid + ";");
            statement.executeUpdate("UPDATE Trainers SET " + time + "= 'Booked' WHERE trainer_id = " + tid + ";");
            statement.close();
            }catch (Exception e){
                e.printStackTrace();
        }
    }

    public static void adminUpdateClass(Scanner in, Connection con)
    {
        int rid, tid, cid;
        int ridog = 0;
        int tidog = 0;
        String time;
        String timeOg = "";
        System.out.println("Which class would you like to update: ");
        cid = in.nextInt();
        in.nextLine();
        System.out.println("Please enter new date of class (Monday, Tuesday, ... , Friday): ");
        time = in.nextLine();
        System.out.println("Please enter the new trainer (ID) you would like to assign to the class: ");
        tid = in.nextInt();
        System.out.println("Please enter the new room the class will take place in: ");
        rid = in.nextInt();
        try{
            Class.forName("org.postgresql.Driver");
            Statement statement = con.createStatement();

            //keep track of original trainer and room
            statement.executeQuery("SELECT trainer_id, room_num, time_slot FROM Classes WHERE class_id = " + cid +";");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                ridog = resultSet.getInt("room_num");
                tidog = resultSet.getInt("trainer_id");
                timeOg = resultSet.getString("time_slot");
            }

            // Update classes
            statement.executeUpdate("UPDATE Classes SET time_slot = '" + time.toUpperCase() + "', trainer_id = " + tid +", room_num = " + rid + " WHERE class_id = " + cid + ";");

            // Find day to update
            if (time.equalsIgnoreCase("monday")) {time = "mon_status";}
            else if (time.equalsIgnoreCase("tuesday")) {time = "tue_status";}
            else if (time.equalsIgnoreCase("wednesday")) {time = "wen_status";}
            else if (time.equalsIgnoreCase("thursday")) {time = "thu_status";}
            else if (time.equalsIgnoreCase("friday")) {time = "fri_status";}

            if (timeOg.equalsIgnoreCase("monday")) {timeOg = "mon_status";}
            else if (timeOg.equalsIgnoreCase("tuesday")) {timeOg = "tue_status";}
            else if (timeOg.equalsIgnoreCase("wednesday")) {timeOg = "wen_status";}
            else if (timeOg.equalsIgnoreCase("thursday")) {timeOg = "thu_status";}
            else if (timeOg.equalsIgnoreCase("friday")) {timeOg = "fri_status";}

            // Update schedule of trainer and room (original and new)
            statement.executeUpdate("UPDATE Rooms SET " + timeOg + "= 'Available' WHERE room_num = " + ridog + ";");
            statement.executeUpdate("UPDATE Trainers SET " + timeOg + "= 'Available' WHERE trainer_id = " + tidog + ";");

            statement.executeUpdate("UPDATE Rooms SET " + time + "= 'Booked' WHERE room_num = " + rid + ";");
            statement.executeUpdate("UPDATE Trainers SET " + time + "= 'Booked' WHERE trainer_id = " + tid + ";");
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void adminDeleteClass(Scanner in, Connection con)
    {
        int rid = 0;
        int cid = 0;
        int tid = 0;

        String time = "";
        System.out.println("Which class would you like to Delete?");
        cid = in.nextInt();

        try{
            Class.forName("org.postgresql.Driver");
            Statement statement = con.createStatement();

            //Find the trainer and room of the soon to be removed class
            statement.executeQuery("SELECT trainer_id, room_num, time_slot FROM Classes WHERE class_id = " + cid +";");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                rid = resultSet.getInt("room_num");
                tid = resultSet.getInt("trainer_id");
                time = resultSet.getString("time_slot");
            }

            // Update amount owed by members who were going to that class
            statement.executeUpdate("UPDATE Members AS m SET amount_owed = amount_owed - 10 FROM Class_Members AS c WHERE c.member_id = m.member_id AND c.class_id = "+ cid + ";");

            // Find day to update
            if (time.equalsIgnoreCase("monday")) {time = "mon_status";}
            else if (time.equalsIgnoreCase("tuesday")) {time = "tue_status";}
            else if (time.equalsIgnoreCase("wednesday")) {time = "wen_status";}
            else if (time.equalsIgnoreCase("thursday")) {time = "thu_status";}
            else if (time.equalsIgnoreCase("friday")) {time = "fri_status";}

            // Update schedule of trainer and room
            statement.executeUpdate("UPDATE Rooms SET " + time + "= 'Available' WHERE room_num = " + rid + ";");
            statement.executeUpdate("UPDATE Trainers SET " + time + "= 'Available' WHERE trainer_id = " + tid + ";");

            //Delete the class
            statement.executeUpdate("DELETE FROM Classes WHERE class_id = " + cid + ";");

            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void adminManageRooms(Scanner in,Connection con,int option)
    {
        int rid = 0; //room number
        String day = "";//day of the work week
        String status = "";//availability
        try{
            Class.forName("org.postgresql.Driver");
            Statement statement = con.createStatement();
            //View Rooms
            if (option == 2)
            {
                statement.executeQuery("SELECT * FROM Rooms;");
                ResultSet resultSet = statement.getResultSet();
                System.out.println("Rooms: ");
                while (resultSet.next()){
                    System.out.print("Room Number: ");
                    System.out.println(resultSet.getString("room_num"));
                    System.out.print("Room Description: ");
                    System.out.println(resultSet.getString("room_desc"));
                    System.out.print("Monday Availability: ");
                    System.out.println(resultSet.getString("mon_status"));
                    System.out.print("Tuesday Availability: ");
                    System.out.println(resultSet.getString("tue_status"));
                    System.out.print("Wednesday Availability: ");
                    System.out.println(resultSet.getString("wen_status"));
                    System.out.print("Thursday Availability: ");
                    System.out.println(resultSet.getString("thu_status"));
                    System.out.print("Friday Availability: ");
                    System.out.println(resultSet.getString("fri_status"));
                    System.out.println("\n");
                }
            }
            // Update Rooms
           if (option == 1)
           {
               System.out.println("Which room would you like to update? (Room Number)");
               rid = in.nextInt();
               in.nextLine();
               System.out.println("Which day would you like to update?");
               day = in.nextLine();
               System.out.println("What would you like to change the status to?");
               status = in.nextLine();

               if (day.equalsIgnoreCase("monday")) {day = "mon_status";}
               else if (day.equalsIgnoreCase("tuesday")) {day = "tue_status";}
               else if (day.equalsIgnoreCase("wednesday")) {day = "wen_status";}
               else if (day.equalsIgnoreCase("thursday")) {day = "thu_status";}
               else if (day.equalsIgnoreCase("friday")) {day = "fri_status";}

               //update to new availability
               statement.executeUpdate("UPDATE Rooms SET " + day + "= '" + status + "' WHERE room_num = " + rid + ";");
           }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void adminSendBill(Scanner in, Connection con)
    {
        try{
            Class.forName("org.postgresql.Driver");
            Statement statement = con.createStatement();
            statement.executeQuery("SELECT fname, lname, amount_owed FROM Members;");
            ResultSet resultSet = statement.getResultSet();
            //Prints money that members ow
            System.out.println("Money Owed By Current Members: ");
            while (resultSet.next()){
                System.out.print("Name: ");
                System.out.print(resultSet.getString("fname"));
                System.out.print(" ");
                System.out.println(resultSet.getString("lname"));
                System.out.print("Amount Owed: ");
                System.out.println(resultSet.getInt("amount_owed"));
                System.out.println("\n");
            }
            String ans = "No";
            // Lets admin decide to send or not send the bills
            System.out.println("Would you like to send the bill? (Yes/No)");
            ans = in.nextLine();

            if (ans.equalsIgnoreCase("Yes"))
            {
                statement.executeUpdate("UPDATE Members SET amount_owed = 0;");
                System.out.println("Bill has been sent");
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
