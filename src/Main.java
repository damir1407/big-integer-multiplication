import java.util.Scanner;

/**
 * Created by damirvaresanovic on 25/04/2017.
 */
public class Naloga2 {
    public static void main(String[] args){
        Scanner sc  = new Scanner(System.in);
        String type = args[0];
        String method = args[1];
        String numberOne = sc.next();
        String numberTwo = sc.next();
        int[] num1 = convertToInt(numberOne);
        int[] num2 = convertToInt(numberTwo);
        switch(method){
            case "os": First(num1,num2,type); break;
            case "ru": Second(num1,num2,type); break;
            case "dv": Third(num1,num2,type); break;
            case "ka": Fourth(num1,num2,type); break;
        }
    }
    public static void First(int[] number1, int[] number2, String type){
        int[] result = new int[number1.length+number2.length];
        int[][] values = new int[number2.length][number1.length+1];
        int countMult=0;
        for(int i = number2.length-1; i >= 0; i--){
            int carry=0;
            int carryV=0;
            for(int j = number1.length-1; j >= 0; j--){
                if(number2[i] != 0) countMult++;
                result[i+j+1] += carry + number2[i] * number1[j];
                values[i][j+1] = carryV + number2[i] * number1[j];

                carry = result[i+j+1] / 10;
                carryV = values[i][j+1] / 10;

                result[i+j+1] = result[i+j+1]%10;
                values[i][j+1] = values[i][j+1]%10;
            }
            result[i] += carry;
            values[i][0] = carryV;
        }
        if(type.equals("trace")) izpisFirst(result,values);
        else System.out.println(countMult);
    }

    public static void Second(int[] number1, int[] number2, String type){
        int[] num2 = new int[number1.length+number2.length];
        int[] result = new int[number1.length+number2.length];
        int countZerosNum1=0;
        int countZerosNum2;
        int countMult = 0;

        for(int i = number2.length-1; i >= 0; i--){
            num2[i+number1.length] = number2[i];
        }

        if(type.equals("trace")) {
            countZerosNum1 = 0;
            for (int i = 0; i < number1.length; i++) {
                if (number1[i] != 0) countZerosNum1++;
                if (countZerosNum1 != 0) System.out.print(number1[i]);
            }
            System.out.print(" ");

            countZerosNum2 = 0;
            for (int i = 0; i < num2.length; i++) {
                if (num2[i] != 0) countZerosNum2++;
                if (countZerosNum2 != 0) System.out.print(num2[i]);
            }

            if (number1[number1.length - 1] % 2 != 0) {
                System.out.println(" 1");
                for (int i = result.length - 1; i >= 0; i--) {
                    result[i] = num2[i];
                }
            } else System.out.println("0");
        }

        while(number1[number1.length-1] != 1 || countZerosNum1 != 1){
            if(type.equals("count")){
                countMult += countMultsSecond(number1);
                countMult += countMultsSecond(num2);
            }

            int num1carry=0;
            for(int i = 0; i < number1.length; i++){
                int temp=number1[i];
                number1[i] = num1carry + number1[i] / 2;
                if(temp%2 != 0) num1carry=5;
                else num1carry=0;
            }

            int num2carry=0;
            for(int i = num2.length-1; i >= 0; i--) {
                num2[i] = num2carry + num2[i] * 2;
                num2carry = num2[i] / 10;
                num2[i] = num2[i] % 10;
            }

            if(type.equals("trace")) {
                countZerosNum1 = 0;
                for (int i = 0; i < number1.length; i++) {
                    if (number1[i] != 0) countZerosNum1++;
                    if (countZerosNum1 != 0) System.out.print(number1[i]);
                }

                System.out.print(" ");

                countZerosNum2 = 0;
                for (int i = 0; i < num2.length; i++) {
                    if (num2[i] != 0) countZerosNum2++;
                    if (countZerosNum2 != 0) System.out.print(num2[i]);
                }
                if (number1[number1.length - 1] % 2 != 0) {
                    System.out.println(" 1");
                    int resultCarry = 0;
                    for (int i = result.length - 1; i >= 0; i--) {
                        result[i] += resultCarry + num2[i];
                        resultCarry = result[i] / 10;
                        result[i] = result[i] % 10;
                    }
                } else System.out.println(" 0");
            }
            else{
                countZerosNum1 = 0;
                for (int i = 0; i < number1.length; i++) {
                    if (number1[i] != 0) countZerosNum1++;
                }
            }
        }
        if(type.equals("trace")) izpisRes(result);
        else System.out.println(countMult);
    }

    public static int countMultThird;
    public static void Third(int[] number1, int[] number2, String type){
        countMultThird=0;
        number1 = getridofzeros(number1);
        number2 = getridofzeros(number2);
        int[] result = MultiplyThird(number1,number2,type);
        if(type.equals("trace")) izpisRes(result);
        else System.out.println(countMultThird);
    }

    public static int[] MultiplyThird(int[] number1, int[] number2, String type){
        if(type.equals("trace")) izpis(number1,number2);
        int n;
        if( number1.length == 1 || number2.length == 1 ) n = 1;
        else {
            n = max(number1.length, number2.length);
            if(n % 2 != 0) n++;
        }
        if( n == 1 ) {
            if(number1[0] == 0 || number2[0] == 0) return new int[]{0};
            if(number1.length == 1){
                countMultThird+=number2.length;
                return mult(number2,number1);
            }
            else{
                countMultThird+=number1.length;
                return mult(number1,number2);
            }
        }

        int diff1 = number1.length - n/2;
        int[] num1left;
        int[] num1right;
        if(diff1 <= number1.length  && diff1 > 0){
            num1left = new int[diff1];
            num1right = new int[number1.length - diff1];
            split( number1, num1left, num1right );
            if(num1right[0] == 0) num1right = getridofzeros(num1right);
        }
        else{
            num1left = new int[1];
            num1right = new int[number1.length];
            num1right = number1;
        }

        int diff2 = number2.length - n/2;
        int[] num2left;
        int[] num2right;
        if(diff2 <= number2.length  && diff2 > 0){
            num2left = new int[diff2];
            num2right = new int[number2.length - diff2];
            split( number2, num2left, num2right );
            if(num2right[0] == 0) num2right = getridofzeros(num2right);
        }
        else{
            num2left = new int[1];
            num2right = new int[number2.length];
            num2right = number2;
        }

        int[] x1 = MultiplyThird(num1right,num2right,type);
        int[] x2 = MultiplyThird(num1right,num2left,type);
        int[] x3 = MultiplyThird(num1left,num2right,type);
        int[] x4 = MultiplyThird(num1left,num2left,type);

        int[] pow1 = createPowArray(n);
        int[] rez1 = mult(x4,pow1);

        int[] addrez;
        if(x2.length > x3.length){
            addrez = add(x2,x3);
        }
        else{
            addrez = add(x3,x2);
        }

        int[] pow2 = createPowArray(n/2);
        int[] rez2 = mult(addrez,pow2);

        int[] rez3;
        if(rez1.length > rez2.length){
            rez3 = add(rez1,rez2);
        }
        else{
            rez3 = add(rez2,rez1);
        }

        int[] result;
        if(rez3.length > x1.length){
            result = add(rez3,x1);
        }
        else{
            result = add(x1,rez3);
        }

        return result;
    }

    public static int countMultFourth;
    public static void Fourth(int[] number1, int[] number2, String type){
        countMultFourth=0;
        number1 = getridofzeros(number1);
        number2 = getridofzeros(number2);
        int[] result = MultiplyFourth(number1,number2,type);
        if(type.equals("trace")) izpisRes(result);
        else System.out.println(countMultFourth);
    }

    public static int[] MultiplyFourth(int[] number1, int[] number2, String type){
        if(type.equals("trace")) izpis(number1,number2);
        int n;
        if( number1.length == 1 || number2.length == 1 ) n = 1;
        else {
            n = max(number1.length, number2.length);
            if(n % 2 != 0) n++;
        }
        if( n == 1 ) {
            if(number1[0] == 0 || number2[0] == 0) return new int[]{0};
            if(number1.length == 1){
                countMultFourth+=number2.length;
                return mult(number2,number1);
            }
            else{
                countMultFourth+=number1.length;
                return mult(number1,number2);
            }
        }

        int diff1 = number1.length - n/2;
        int[] num1left;
        int[] num1right;
        if(diff1 <= number1.length  && diff1 > 0){
            num1left = new int[diff1];
            num1right = new int[number1.length - diff1];
            split( number1, num1left, num1right );
            if(num1right[0] == 0) num1right = getridofzeros(num1right);
        }
        else{
            num1left = new int[1];
            num1right = new int[number1.length];
            num1right = number1;
        }

        int diff2 = number2.length - n/2;
        int[] num2left;
        int[] num2right;
        if(diff2 <= number2.length  && diff2 > 0){
            num2left = new int[diff2];
            num2right = new int[number2.length - diff2];
            split( number2, num2left, num2right );
            if(num2right[0] == 0) num2right = getridofzeros(num2right);
        }
        else{
            num2left = new int[1];
            num2right = new int[number2.length];
            num2right = number2;
        }

        int[] x1 = MultiplyFourth(num1right,num2right,type);
        int[] x2 = MultiplyFourth(num1left,num2left,type);

        int[] mult1;
        if(num1left.length > num1right.length){
            mult1 = add(num1left,num1right);
            if(mult1[0] == 0) mult1 = getridofzeros(mult1);
        }
        else{
            mult1 = add(num1right,num1left);
            if(mult1[0] == 0) mult1 = getridofzeros(mult1);
        }

        int[] mult2;
        if(num2left.length > num2right.length){
            mult2 = add(num2left,num2right);
            if(mult2[0] == 0) mult2 = getridofzeros(mult2);
        }
        else{
            mult2 = add(num2right,num2left);
            if(mult2[0] == 0) mult2 = getridofzeros(mult2);
        }

        int[] x3 = MultiplyFourth(mult1,mult2,type);
        x1 = getridofzeros(x1);
        x2 = getridofzeros(x2);
        x3 = getridofzeros(x3);

        int[] pow1 = createPowArray(n);
        int[] rez1 = mult(x2,pow1);

        int[] addrez;
        addrez = sub(x3, x2);
        addrez = sub(addrez,x1);

        int[] pow2 = createPowArray(n/2);
        int[] rez2 = mult(addrez,pow2);

        int[] rez3;
        if(rez1.length > rez2.length){
            rez3 = add(rez1,rez2);
        }
        else{
            rez3 = add(rez2,rez1);
        }

        int[] result;
        if(rez3.length > x1.length){
            result = add(rez3,x1);
        }
        else{
            result = add(x1,rez3);
        }

        return result;
    }

    //DRUGA,TRECA I CETVRTA
    public static void izpisRes(int[] result){
        int countZerosResult=0;
        for(int i = 0; i < result.length; i++){
            if(result[i] != 0) countZerosResult++;
            if(countZerosResult != 0) System.out.print(result[i]);
        }
        System.out.println();
    }

    //TRECA I CETVRTA
    public static void izpis(int[] num1, int[] num2){
        for(int i = 0; i < num1.length; i++){
            System.out.print(num1[i]);
        }
        System.out.print(" ");

        for(int i = 0; i < num2.length; i++){
            System.out.print(num2[i]);
        }
        System.out.println();
    }

    public static int[] createPowArray(int n){
        int[] novi = new int[n+1];
        novi[0] = 1;
        for(int i = 1; i < novi.length; i++){
            novi[i] = 0;
        }
        return novi;
    }

    public static int max(int a, int b){
        if(a > b) return a;
        else return b;
    }

    public static void split( int[] num, int[] numLeft, int[] numRight ){
        for( int i = 0; i < numLeft.length; i++ ){
            numLeft[i] = num[i];
        }
        for( int i = numLeft.length, j = 0; i < num.length; i++, j++){
            numRight[j] = num[i];
        }
    }

    public static int[] getridofzeros(int[] array){
        int newIndex=0;
        int cnt=0;
        for(int i = 0; i < array.length; i++){
            if(array[i] != 0){
                cnt++;
                newIndex = i;
                break;
            }
        }
        if(cnt != 0) {
            int[] novi = new int[array.length - newIndex];
            for (int i = 0; i < novi.length; i++) {
                novi[i] = array[i + newIndex];
            }
            return novi;
        }
        else return new int[]{0};
    }

    public static int[] mult(int[] num1, int[] num2){
        int[] novi = new int[num1.length+num2.length];
        for(int i = num2.length-1; i >= 0; i--){
            int num1carry=0;
            for(int j = num1.length-1; j >= 0; j--){
                novi[i+j+1] += num1carry + num2[i] * num1[j];
                num1carry = novi[i+j+1] / 10;
                novi[i+j+1] = novi[i+j+1]%10;
            }
            novi[i] += num1carry;
        }
        return novi;
    }

    public static int[] add( int[] num1, int[] num2 ){
        int[] result = new int[num1.length + 1];
        for( int i = num1.length-1; i >= 0; i-- ){
            result[i+1] = num1[i];
        }

        int[] temp2 = new int[num1.length + 1];
        int diff = temp2.length - num2.length;
        for( int i = num2.length-1; i >= 0; i-- ){
            temp2[i+diff] = num2[i];
        }

        int carry=0;
        for( int i = temp2.length-1; i >= 0; i-- ){
            int r = carry + result[i] + temp2[i];
            result[i] = r % 10;
            carry = r / 10;
        }
        return result;
    }

    public static int[] sub( int[] num1, int[] num2 ){
        int[] temp2 = new int[num1.length];
        int diff = temp2.length - num2.length;
        for(int i = num2.length-1; i >= 0; i--){
            temp2[i+diff] = num2[i];
        }
        for( int i = temp2.length-1; i >= 0; i-- ){
            if(num1[i] < temp2[i]){
                num1[i] += 10;
                num1[i] -= temp2[i];
                num1[i-1] -= 1;
            }
            else{
                num1[i] -= temp2[i];
            }
        }
        return num1;
    }

    //DRUGA
    public static int countMultsSecond(int[] array){
        int count=0;
        int flag=0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) flag++;
            if(flag != 0){
                count = array.length - i;
                break;
            }
        }
        return count;
    }

    //PRVA
    public static void izpisFirst(int[] result, int[][] values){
        int countZeros=0;
        for(int i = 0; i < values.length; i++){
            for(int j = 0; j < values[i].length; j++){
                if(values[i][j] != 0) countZeros++;
                if(countZeros != 0) System.out.print(values[i][j]);
            }
            if(countZeros==0) System.out.print("0");
            countZeros=0;
            System.out.println();
        }

        countZeros=0;
        int firstDigIndex=0;
        int lastDigIndex=result.length;
        for(int i = 0; i < result.length; i++){
            if(result[i] != 0) countZeros++;
            if(countZeros==1){
                firstDigIndex=i;
                break;
            }
        }
        if(countZeros==0) lastDigIndex = firstDigIndex+1;
        for(int j = firstDigIndex; j < lastDigIndex; j++){
            System.out.print("-");
        }
        System.out.println();
        for(int j = firstDigIndex; j < lastDigIndex; j++){
            System.out.print(result[j]);
        }
        System.out.println();
    }

    //SVE
    public static int[] convertToInt(String number){
        int[] novi = new int[number.length()];
        for(int i = 0; i < number.length(); i++){
            novi[i] = number.charAt(i) - '0';
        }
        return novi;
    }

}
