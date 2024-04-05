public class Wordle {
    public static void main(String[] args){
        for(int i = 0; i < 100; i++){
            int num = (int)(Math.random() * i);
            System.out.println((int)(Math.random() * i) + " " + i + " " + (i == num));
        }
    }
}
