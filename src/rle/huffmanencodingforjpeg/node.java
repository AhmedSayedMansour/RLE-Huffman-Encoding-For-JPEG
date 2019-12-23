package rle.huffmanencodingforjpeg;

public class node {

    public node parent;
    public node left;
    public node right;
    
    public String data;
    public String code;
    public Integer prob;

    public node(){
        parent = left = right = null;
        data = code = "";
    }
}
