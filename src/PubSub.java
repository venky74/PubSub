import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Update by vyadav on 3/3/16.
 */

class Controller{

    public static ArrayList<Publisher> publishers = new ArrayList<>();
    public static Hashtable <String, Topics> topicTable = new Hashtable<>();
}

 class Publisher{

    Message msg;

    Publisher(String pub){
        Controller.publishers.add(this);
        System.out.println(pub + " Created");
    }
    
    public boolean publish(Message msg){

        this.msg = msg;
        System.out.println("Message Publish from Publisher " + this + " to Topic " + this.msg.topic + " Message is - " + this.msg.body);

        if (Controller.topicTable.containsKey(this.msg.topic)) {
            Controller.topicTable.get(this.msg.topic).add(this.msg.body);
        }else{
            Topics ntopic = new Topics(this.msg.topic);
            ntopic.add(this.msg.body);
            Controller.topicTable.put(this.msg.topic, ntopic);
        }


        return true;
    }
}

class Topics {

    String topicName;
    ArrayList<Subscriber> subscribers = new ArrayList<>();
    ArrayList<String> topicItems = new ArrayList<>();

    Topics(String topicName) {
        this.topicName = topicName;
        Controller.topicTable.put(this.topicName,this);
        System.out.println(topicName + " Created");
    }

    public boolean add(String msgBody) {
        System.out.println(this + " received message " + msgBody + " for subscribers " + this.subscribers + " " + "Dispatch message");
        dispatchMessage(msgBody);
        return this.topicItems.add(msgBody);
    }

    public boolean addSubscriber(Subscriber subscriber) {

        return this.subscribers.add(subscriber);
    }

    public void dispatchMessage(String msgBody) {
        System.out.println("Messge Dispatched to : " + this.subscribers);

        for (Object subscriber : this.subscribers) {
            Subscriber sc = (Subscriber) subscriber;
            sc.displayMessage(msgBody);

        }
    }
}


class Subscriber{

    public Subscriber(Topics topicName){
        System.out.println("Subscriber Created " + this + " bound to topic " + topicName.topicName );
        topicName.addSubscriber(this);
    }

    public void displayMessage(String msgBody){

        System.out.println("Message received " + this + " " + msgBody);
    }

}

class Message{

    String topic;
    String body;

    Message(String topic, String body ){

        this.topic = topic;
        this.body = body;

    }

    public void displayMessage(){

        System.out.println(this.body);
    }

 }

public class PubSub {

    public static void main(String args[])
    {

        Publisher pu1 = new Publisher("Publisher1");
        Publisher pu2 = new Publisher("Publisher2");
        System.out.println("===============================================");

        Topics topic1 = new Topics("topic1");
        Topics topic2 = new Topics("topic2");
        System.out.println("===============================================");

        Subscriber sub1 = new Subscriber(topic1);
        Subscriber sub2 = new Subscriber(topic1);
        Subscriber sub3 = new Subscriber(topic2);
        System.out.println("===============================================");

        boolean b = pu1.publish(new Message("topic1", "Need Uber"));
        System.out.println("===============================================");

        boolean c = pu1.publish(new Message("topic1", "Need UberX"));
        System.out.println("===============================================");

        boolean d = pu1.publish(new Message("topic2", "Need Uber"));
        System.out.println("===============================================");

        System.out.println("===============================================");

    }

}
