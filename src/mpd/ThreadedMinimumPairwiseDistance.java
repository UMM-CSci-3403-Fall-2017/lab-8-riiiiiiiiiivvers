package mpd;

public class ThreadedMinimumPairwiseDistance implements MinimumPairwiseDistance {

    @Override
    public int minimumPairwiseDistance(int[] values)  {
        Result res1 = new Result();
        Result res2 = new Result();
        Result res3 = new Result();
        Result res4 = new Result();
        MpdThread thread1 = new MpdThread(values, 1, res1);
        MpdThread thread2 = new MpdThread(values, 2, res2);
        MpdThread thread3 = new MpdThread(values, 3, res3);
        MpdThread thread4 = new MpdThread(values, 4, res4);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e){
            //System.out.println("One or more threads interrupted: " + e);
        }

        return Math.min(Math.min(res1.getLowest(), res2.getLowest()), Math.min(res3.getLowest(), res4.getLowest()));
    }

    private class MpdThread extends Thread {
        private Result res;
        private int[] values;
        private int state;

        private MpdThread(int[] values, int state, Result res){
            this.res = res;
            this.values = values;
            this.state = state;
        }

        public void run(){
            int len = values.length;
            if (state == 1){
                for(int i = 0; i < len / 2; i++){
                    for (int j = 0; j < i; j++){
                        res.compare(values[i], values[j]);
                    }
                }
            } else if (state == 2){
                for (int i = len / 2; i < len; i++){
                    for (int j = 0; j < i - (len / 2); j++){
                        res.compare(values[i], values[j]);
                    }
                }
            } else if (state == 3){
                for (int i = len / 2; i < len; i++){
                    for (int j = len / 2; j < i; j++){
                        res.compare(values[i], values[j]);
                    }
                }
            } else {
                for (int i = len / 2; i < len; i++){
                    for (int j = i - (len / 2); j < len / 2; j++){
                        res.compare(values[i], values[j]);
                    }
                }
            }
        }
    }

    private class Result {
        private int lowest;

        private Result (){
            this.lowest = Integer.MAX_VALUE;
        }

        public boolean compare(int n, int m) {
            if (Math.abs(n - m) < lowest) {
                lowest = Math.abs(n-m);
                return true;
            }
            return false;
        }

        public int getLowest(){
            return lowest;
        }
    }
}
