package common;

import java.util.ArrayList;

import entities.Park;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import logic.Order;

public class StaticClass {
    public static String userid;
    public static String username;
    public static boolean islogout =false;
    public static Order o1 = new Order(null, null, null, null, null, null,null);
    public static String typeacc;
	public static boolean isexist;
	public static boolean islogged;
    public static ArrayList<Park> parks = new ArrayList<>();
    public static int numberofvisitors;
    public static String orderdetails="";
    public static String reservationtype;
    public static double discount;
    public static int parkprice;
	public static int available=0;
	public static String maxorderid="";
	public static String dwelltime;
    public static ArrayList<Order> ordersforapprovetable = new ArrayList<>();
    public static ArrayList<Order> ordersforwaitingtable = new ArrayList<>();
    public static String amountinparkyarden="";
    public static int istheorderexist=0;
    public static int amoutgreaterthenorder=0;


}
