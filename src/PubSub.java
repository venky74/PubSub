import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by vyadav on 2/25/16.
 */

 class Publisher{

    Message msg;

    public static Hashtable <String, ArrayList> topicTable = new Hashtable<String, ArrayList>();

    Publisher(String pub){
        System.out.println(pub + " Created");
    }
    
    public boolean publish(Message msg){

        this.msg = msg;
       // System.out.println("Topic " + this.msg.topic);
       // System.out.println("Body " + this.msg.body);

        if (topicTable.containsKey(this.msg.topic)) {
            topicTable.get(this.msg.topic).add(this.msg.body);
        }
        else {
            ArrayList msgList = new ArrayList();
            msgList.add(this.msg.body);
            ArrayList b = topicTable.put(this.msg.topic, msgList);
        }

        return true;
    }



}


class Subscriber{

    String tp = new String();

    public Subscriber(){
        System.out.println("Subscriber Created");

    }


    public ArrayList topicLookup(String topicName) {
        this.tp = topicName;
        ArrayList arrayList = Publisher.topicTable.get(this.tp);
        return arrayList;
    }
}

class Message{

    String topic = new String();
    String body = new String();

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

        boolean b = pu1.publish(new Message("topic1", "Need Uber"));
        boolean c = pu1.publish(new Message("topic1", "Need UberX"));

        boolean x = pu1.publish(new Message("topic2", "Need Uber t2"));
        boolean y = pu1.publish(new Message("topic2", "Neex UberX t2"));


        Subscriber sub1 = new Subscriber();
        ArrayList topicList = sub1.topicLookup("topic1");

        System.out.println("topic1 " + topicList);

        Subscriber sub2 = new Subscriber();
        ArrayList topicList2 = sub2.topicLookup("topic2");

        System.out.println("topic2 " + topicList2);

        Subscriber sub3 = new Subscriber();
        ArrayList topicList3 = sub3.topicLookup("topic1");

        System.out.println("topic3 " + topicList3);


    }

}
