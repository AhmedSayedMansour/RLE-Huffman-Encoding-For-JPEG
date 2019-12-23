package rle.huffmanencodingforjpeg;
import java.util.*;

public class Huffman implements Comparator<node> {
    public static HashMap<String,Integer> map = new HashMap<String,Integer>();

    public static void setCodeToTree(node root){
        if(root == null){
            return;
        }
        if(root.parent == null){
            root.left.code = "1";
            if (root.right!=null){root.right.code = "0";}
            setCodeToTree(root.left);
            setCodeToTree(root.right);
        }
        else{
            if(root.left!=null){
                root.left.code = root.code + "1";
                setCodeToTree(root.left);
            }
            if(root.right!=null){
                root.right.code = root.code + "0";
                setCodeToTree(root.right);
            }
        }
    }

    public static void calcSeq(ArrayList<String> s){
        for(int i = 0; i < s.size(); i++){
            String inString = s.get(i);

            if(map.containsKey(inString)){
                map.put(inString,map.get(inString)+1);
            }else{
                map.put(inString,1);
            }
        }
    }

    @Override
    public int compare(node o1, node o2) {
        return o1.prob.compareTo(o2.prob);
    }

    public static String code;
    public static void getCode(node root,String c){
        if(root == null){
            return;
        }
        if (root.data.equals(c)){
            code = root.code;
        }
        else{
            getCode(root.left, c);
            getCode(root.right, c);
        }
    }

    public HashMap<String,String> getHuffmanTable (ArrayList<String> s){
        node root = new node();
        ArrayList<node> arr = new ArrayList<>();
        HashMap<String,String> output = new HashMap<>();
        calcSeq(s);
        for ( Map.Entry<String,Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer val = entry.getValue();
            node n = new node();
            n.data =  key;
            n.prob = val;
            arr.add(n);
        }
        
        while(arr.size()>2){
            Collections.sort(arr , new Huffman());
            node n = new node();
            n.left = arr.get(0);
            n.right = arr.get(1);
            n.prob = n.left.prob + n.right.prob;
            n.left.parent = n;
            n.right.parent = n;
            arr.remove(0);
            arr.remove(0);
            arr.add(n);
        }
        
        if(arr.size()==2){
            root.left = arr.get(0);
            root.right = arr.get(1);
            root.left.parent = root;
            root.right.parent = root;
            arr.remove(0);
            arr.remove(0);
        }else{          //if one character
            root.left = arr.get(0);
            root.left.parent = root;
            arr.remove(0);
        }
        setCodeToTree(root);
        for(int i=0;i<s.size();i++) {
            String c = s.get(i);
            getCode(root, c);
            output.put(c, code);
        }
        return output;
    }
}
