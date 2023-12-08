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
        String[] players=new String[]{"johnson","yuguai","zh1016"};
       Set<Integer> indexs=new HashSet<Integer>();//已发牌标记并去重
        List<Brand> pubList=new ArrayList<>();//公共牌
        for (int i=0;i<5;i++){
            int index=0;
            do {
                Brand flop = (Brand) getBrand(brands).get("brand");
                 index = (int) getBrand(brands).get("index");
                 if(!indexs.contains(index)){
                     pubList.add(flop);//公共牌5张
                     indexs.add(index);
                     System.out.println(index);

                 }
            }while(!indexs.contains(index));
        }
        System.out.println("======");
       for (int i=0;i<players.length;i++){
           Player p1 =new Player();
            p1.setName(players[i]);
           List<Brand> pb=new ArrayList<>();//玩家当前手牌
           int index1=0;
           int index2=0;
           do{
                Brand b1=(Brand) getBrand(brands).get("brand");
               index1 = (int) getBrand(brands).get("index");
               if(!indexs.contains(index1)) {
                   pb.add(b1);
                   indexs.add(index1);
                   System.out.println(index1);
               }
           }while(!indexs.contains(index1));
            do{
                Brand b2=(Brand) getBrand(brands).get("brand");
                index2 = (int) getBrand(brands).get("index");
                if(!indexs.contains(index2)) {
                    pb.add(b2);
                    indexs.add(index2);
                    System.out.println(index2);
                }
            }while(!indexs.contains(index2));
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
