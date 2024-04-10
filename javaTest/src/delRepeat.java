import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
//德州扑克比大小
public class delRepeat {
    public static void main(String[] args) {
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
        String[] players=new String[]{"yuguai","zh1016","johnson","huangzong"};
       Set<Integer> indexs=new HashSet<Integer>();//已发牌标记并去重
        List<Brand> pubList=new ArrayList<>();//公共牌
        int len=0;

        System.out.println("======");

        Map<String,Player> playersMap=new HashMap<String,Player>();
       for (int i=0;i<players.length;i++){
           Player p1 =new Player();
            p1.setName(players[i]);
           List<Brand> pb=new ArrayList<>();//玩家当前手牌
           int index1=0;
           int index2=0;
           //开局每人两张牌
           //玩家第一张牌
           do{
               Map m1=new HashMap();
               m1=getBrand(brands);//正常玩家
               if(players[i]=="johnson"){//作弊玩家
                   m1=getCheatBrand(brands);
                   index1 = (int) m1.get("index");
                   if(indexs.contains(index1)){
                       m1=getBrand(brands);
                   }
               }
               Brand b1 = (Brand) m1.get("brand");
               index1 = (int) m1.get("index");

               len=indexs.size();

               if(!indexs.contains(index1)) {
                   pb.add(b1);
                   indexs.add(index1);
               }
           }while(len==indexs.size());
           //玩家第二张牌
            do{
                Map m1=new HashMap();
                m1=getBrand(brands);//正常玩家
                if(players[i]=="johnson"){//作弊玩家
                    m1=getCheatBrand(brands);
                    index2 = (int) m1.get("index");
                    if(indexs.contains(index2)){
                        m1=getBrand(brands);
                    }
                }
                Brand b2 = (Brand) m1.get("brand");
                index2 = (int) m1.get("index");
                len=indexs.size();
                if(!indexs.contains(index2)) {//至少做一次，不存在就插入
                    pb.add(b2);
                    indexs.add(index2);
                }
            }while(len==indexs.size());
           p1.setUbrands(pb);
           playersMap.put(players[i],p1);


       }
        //  5张公共牌一次性全发（可改进为翻牌转牌河牌）
        for (int i=0;i<5;i++) {
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
        //玩家手牌汇总（可将自己手牌作弊（^_^））
        for (int i=0;i<players.length;i++){
            Player p1=playersMap.get(players[i].toString());
            List<Brand> pb=p1.getUbrands();
            //玩家手牌加入公共牌
            for(Brand b :pubList ){
                pb.add(b);
            }
            p1.setUbrands(pb);
            System.out.println(players[i]+"的牌为：");
            for(Brand b :pb ){
                System.out.print(b.getDecor()+b.getPoint()+" ");
            }
            System.out.println();
            playersMap.put(players[i].toString(),p1);
        }
        System.out.println("=====================");
        //玩家牌型保存
        for (int i=0;i<players.length;i++){
            Player p1=playersMap.get(players[i].toString());
            //玩家当前7张牌
            List<Brand> pl=p1.getUbrands();
           //同花色分组统计
           Map<String,Long> decorMap= (Map<String, Long>) pl.stream().collect(Collectors.groupingBy(Brand::getDecor,Collectors.counting()));
           //同点数分组统计
           Map<String,Long> pointMap= (Map<String, Long>) pl.stream().collect(Collectors.groupingBy(Brand::getPoint,Collectors.counting()));
           System.out.println(decorMap);
           System.out.println(pointMap);
            Long sameDecor= Long.valueOf(0);//同花数量
            int straightNum= 0;//同花数量
            int straightFlushNum= 0;//同花顺数量
            int highPoint=0;
            List highDecor=new ArrayList();//高张列表，最多7个
            List doubleDecor=new ArrayList();//；对子点数列表，最多3个
            List setDecor=new ArrayList();//三条点数列表，最多2个
            List quadsDecor=new ArrayList();//四条列表，最多1个
            int doublePoint=0;
            int setPoint=0;
            int quadsPoint=0;
            List pointlist=new ArrayList();//所有的点数列表
           Iterator<Map.Entry<String,Long>> iterator1=pointMap.entrySet().iterator();
           Iterator<Map.Entry<String,Long>> iterator2=decorMap.entrySet().iterator();
           while(iterator1.hasNext()){//点数判定，四条，葫芦，顺子
               Map.Entry<String,Long> entry= iterator1.next();
               String key = entry.getKey();
               Long  value= entry.getValue();
//               int value =new  Integer(String.valueOf(v));
               pointlist.add(key);
               if(value==4){
                   quadsPoint++;
                   quadsDecor.add(key);
               }else if(value==3){
                   setPoint++;
                   setDecor.add(key);
               }else if (value==2){
                   doublePoint++;
                   doubleDecor.add(key);
               }else{
                   highPoint++;
                   highDecor.add(key);
               }
           }

            int [] highDecorSort=sortPoint(highDecor);
            int [] doubleDecorSort=sortPoint(doubleDecor);
            int [] setDecorSort=sortPoint(setDecor);
            int [] quadsDecorSort=sortPoint(quadsDecor);
           List flushList=new ArrayList();
           while(iterator2.hasNext()) {//同花色
               Map.Entry<String, Long> entry = iterator2.next();
               String key = entry.getKey();
               Long  value=entry.getValue();
//               int value =new  Integer(String.valueOf(v));
               if(value>=5){
                   sameDecor=value;
                   for(Brand b :pl ){
                       if(b.getDecor()==key){
                           flushList.add(b.getPoint());//同花还需要判断同花的5张牌是否为顺子
                       }

                   }
//                   System.out.println("牌型为同花");
//                   continue;
               }
           }
            int [] flushPoint=sortPoint(flushList);

            int [] sort=sortPoint(pointlist);
            int maxStr=0;
           for (int j=1;j<sort.length;j++){
               if(sort[j]==sort[j-1]+1&&sort[j]!=0){
                   straightNum++;
                   maxStr=sort[j];
               }else{
                   straightNum=0;
                   maxStr=0;
               }

           }
           for (int j=1;j<flushPoint.length;j++){
               if(flushPoint[j]==flushPoint[j-1]+1&&flushPoint[j]!=0){
                   straightFlushNum++;
               }else{
                   straightFlushNum=0;
               }
           }
            System.out.print(p1.getName()+"的");


            int q=quadsDecorSort[quadsDecorSort.length-1];//四条

            int s1=setDecorSort[setDecorSort.length-1];//三条列表最大那个
            int s2=setDecorSort[setDecorSort.length-2];//

            int d1=doubleDecorSort[doubleDecorSort.length-1];//最大对子
            int d2=doubleDecorSort[doubleDecorSort.length-2];

            int h1=highDecorSort[highDecorSort.length-1];//最大高牌
            int h2=highDecorSort[highDecorSort.length-2];//第二大高牌
            int h3=highDecorSort[highDecorSort.length-3];//第三大高牌
            int h4=highDecorSort[highDecorSort.length-4];//第四大高牌
            int h5=highDecorSort[highDecorSort.length-5];//最小高牌

            int f1=flushPoint[flushPoint.length-1];//最大同花牌
            int f2=flushPoint[flushPoint.length-2];//第二大同花牌
            int f3=flushPoint[flushPoint.length-3];//第三大同花牌
            int f4=flushPoint[flushPoint.length-4];//第四大同花牌
            int f5=flushPoint[flushPoint.length-5];//最小同花牌

            int str1=sort[sort.length-1];//最大顺子牌
            List<Integer> kList=new ArrayList<>();
           if(straightFlushNum>=4) {
               System.out.println("牌型为同花顺");
               p1.setLevel(9);
               kList.add(maxStr);//整体5
           }else if(quadsPoint>0){
               System.out.println("牌型为四条"+q);
               p1.setLevel(8);
               kList.add(q);//4
               kList.add(h1);//1
           }else if(setPoint==1&&doublePoint>0||setPoint>1){
               if(d1>s2){
                   s2=d1;
               }
               System.out.println("牌型为"+getValue(s1)+" "+getValue(s2)+"葫芦");
               p1.setLevel(7);
               kList.add(s1);//3
               kList.add(s2);//2
           }else if(sameDecor>=5){
               System.out.println("牌型为同花"+getValue(f1));
               p1.setLevel(6);
               kList.add(f1);//1
               kList.add(f2);//1
               kList.add(f3);//1
               kList.add(f4);//1
               kList.add(f5);//1
           }else if(straightNum>=4){
               System.out.println("牌型为顺子"+getValue(maxStr));
               p1.setLevel(5);
               kList.add(maxStr);//整体5
           } else if(doublePoint==0&&setPoint==1){
               System.out.println("牌型为三条"+getValue(s1));
               p1.setLevel(4);
               kList.add(s1);//3
               kList.add(h1);//1
               kList.add(h2);//1
           } else if(doublePoint>1&&setPoint==0){
               System.out.println("牌型为"+getValue(d1)+" "+getValue(d2)+"两对");
               p1.setLevel(3);
               kList.add(d1);//2
               kList.add(d2);//2
               kList.add(h1);//1

           } else if(doublePoint==1&&setPoint==0){
               System.out.println("牌型为一对"+getValue(d1));
               p1.setLevel(2);
               kList.add(d1);//2
               kList.add(h1);//1
               kList.add(h2);//1
               kList.add(h3);//1
           } else{
               System.out.println("牌型为高牌"+getValue(h1));
               p1.setLevel(1);
               kList.add(h1);//1
               kList.add(h2);//1
               kList.add(h3);//1
               kList.add(h4);//1
               kList.add(h5);//1
           }
            p1.setKick(kList);
            playersMap.put(players[i].toString(),p1);
        }
        //玩家牌型比较
        Set<String> maxPlayers=new HashSet();
        //一共9种牌型
        int maxLevel=0;
        List<Integer> maxKick=new ArrayList();

        List<Integer> lastKick=new ArrayList();
        int[] initial=new int[]{0,0,0,0,0,};
        for (int i : initial) {
            lastKick.add(i);
        }
        for (int i=0;i<players.length;i++){
            Player p1=playersMap.get(players[i]);
            String name=p1.getName();
            int currentLevel=p1.getLevel();
            List<Integer> currentKick=p1.getKick();
            if(currentLevel>maxLevel){
                maxLevel=currentLevel;
                maxPlayers.clear();
                maxPlayers.add(name);
                maxKick= currentKick;
            }else if(currentLevel==maxLevel){
                for(int j=0;j<currentKick.size();j++){
                    int ck=currentKick.get(j);//当前玩家踢脚
                    if(ck>maxKick.get(j)){
                        maxKick.set(j,ck);
                        maxPlayers.clear();
                        maxPlayers.add(name);
                    }else if(ck==maxKick.get(j)){//踢脚相同，继续比下一个踢脚
                        continue;
                    }else{
                        break;
                    }

                }

            }else{
                continue;
            }
        }
        System.out.print("赢家是");
        for (String winner:maxPlayers){
            System.out.println(winner);
        }


    }
    //获取任意一张牌
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
    //AK作弊器（低级）
    public static Map getCheatBrand(List<Brand> b){
        Random r=new Random();
        int num=r.nextInt(7)+b.size()-8;//在52张牌中不是A就是K
        Brand rb=b.get(num);
//        System.out.print(rb.getDecor()+rb.getPoint());//任意一张牌
        Map result=new HashMap();
        result.put("brand",rb);
        result.put("index",num);
        return   result;
    }

    //将字母数字列表排序的通用方法
    public static int[] sortPoint(List list){
        Object[] decorArray=  list.toArray();
        int[] sort=new int[8];
        for (int j=0;j<decorArray.length;j++){
            if(decorArray[j]=="10"){
                sort[j]=10;
            }else if(decorArray[j]=="J"){
                sort[j]=11;
            }else if(decorArray[j]=="Q"){
                sort[j]=12;
            }else if(decorArray[j]=="K"){
                sort[j]=13;
            }else if(decorArray[j]=="A"){
                sort[j]=14;
                sort[j+1]=1;
//                j=j+1;
            }else{
                sort[j]= Integer.parseInt(String.valueOf(decorArray[j]));
            }
        }
        Arrays.sort(sort);
        return  sort;
    }
    //数字转扑克点数
    public static String  getValue(int i){
        String v;
        if(i==10){
                v="10";
            }else if(i==11){
                v="J";
            }else if(i==12){
                v="Q";
            }else if(i==13){
                v="K";
            }else if(i==14){
                v="A";
            }else{
                v=String.valueOf(i);
            }
        return  v;
    }
}
