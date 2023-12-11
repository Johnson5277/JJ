import java.util.*;

public class delRepeat {
    public static void main(String[] args) {
//        List<String> l =new ArrayList();
//        l.add("d");
//        l.add("aaa");
//        l.add("b");
//        l.add("a");
//        l.add("c");
//        l.add("c");
//        System.out.println(l);
//        Set s=new HashSet(l);
//        Iterator iterator=s.iterator() ;
//        if(iterator.hasNext()){
//
//        }
//        List<String> l2=new ArrayList(s);
//        System.out.println(l2);

       String[] point=new String[]{"2","3","4","5","6","7","8","9","10","J","Q","K","A"};//点数
       String[] decor=new String[]{"♦","♥","♣","♠"};//花色
        List<Brand> brands=new ArrayList<>();//牌堆
       for(int i=0;i<point.length;i++){
           for(int j=0;j<decor.length;j++){
               Brand b=new Brand();
               b.setDecor(decor[j]);
               b.setPoint(point[i]);
               brands.add(b);
           }
       }
//        String[] players=new String[]{"johnson","yuguai"};
        String[] players=new String[]{"johnson","yuguai","zh1016"};
       Set<Integer> indexs=new HashSet<Integer>();//已发牌标记并去重
        List<Brand> pubList=new ArrayList<>();//公共牌
        int len=0;
        for (int i=0;i<5;i++){
            int index=0;
            do {
                Map m1=new HashMap();
                m1=getBrand(brands);
                Brand flop = (Brand) m1.get("brand");
                 index = (int) m1.get("index");
//                System.out.print(index+",");
//                System.out.println(flop.getDecor()+flop.getPoint());
                 len=indexs.size();
                 if(!indexs.contains(index)){//至少做一次，不存在就插入
                     pubList.add(flop);//公共牌5张
                     indexs.add(index);
                 }
//                System.out.println(indexs);
            }while(len==indexs.size());//至少加一次，如果不包含就一直加
        }
        System.out.println("======");
       for (int i=0;i<players.length;i++){
           Player p1 =new Player();
            p1.setName(players[i]);
           List<Brand> pb=new ArrayList<>();//玩家当前手牌
           int index1=0;
           int index2=0;
           do{
               Map m1=new HashMap();
               m1=getBrand(brands);
               Brand b1 = (Brand) m1.get("brand");
               index1 = (int) m1.get("index");
               len=indexs.size();
//               System.out.print(index1+",");
//               System.out.println(b1.getDecor()+b1.getPoint());
               if(!indexs.contains(index1)) {
                   pb.add(b1);
                   indexs.add(index1);
               }
//               System.out.println(indexs);
           }while(len==indexs.size());
            do{
                Map m1=new HashMap();
                m1=getBrand(brands);
                Brand b2 = (Brand) m1.get("brand");
                index2 = (int) m1.get("index");
                len=indexs.size();
//                System.out.print(index2+",");
//                System.out.println(b2.getDecor()+b2.getPoint());
                if(!indexs.contains(index2)) {//至少做一次，不存在就插入
                    pb.add(b2);
                    indexs.add(index2);
                }
//                System.out.println(indexs);
            }while(len==indexs.size());
           //开局每人两张牌
           for(Brand b :pubList ){
               pb.add(b);
           }
           p1.setUbrands(pb);
           System.out.println(players[i]+"的牌为：");
           for(Brand b :pb ){
               System.out.print(b.getDecor()+b.getPoint());
           }
           System.out.println();
       }


    }
    public static Map getBrand(List<Brand> b){
        Random r=new Random();
        int num=r.nextInt(b.size()-1);
        Brand rb=b.get(num);
//        System.out.print(rb.getDecor()+rb.getPoint());//任意一张牌
        Map result=new HashMap();
        result.put("brand",rb);
        result.put("index",num);
       return   result;
    }
}
