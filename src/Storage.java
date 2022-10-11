import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class Storage {
    private LinkedList<Object> List = new LinkedList<Object>();//仓库载体
    final Semaphore notFull = new Semaphore(10);//仓库最大容量
    final Semaphore notEmpty = new Semaphore(0);//将线程挂起等其他来触发
    final Semaphore mutex = new Semaphore(1);//互斥锁

    public void producer(){
        try{
            notFull.acquire();
            mutex.acquire();
            List.add(new Object());
            System.out.println(
                    "The Producer Thread [" + Thread.currentThread().getName() + "] has added one successfully."
                            + "\tNow the Storage_Size : " + List.size()
            );
        }catch (Exception exception){exception.printStackTrace();}
        finally {
            mutex.release();
            notEmpty.release();
        }
    }

    public void consumer(){
        try{
            notEmpty.acquire();
            mutex.acquire();
            List.remove();
            System.out.println(
                    "The Consumer Thread [" + Thread.currentThread().getName() + "] has removed one successfully."
                            + "\tNow the Storage_Size : " + List.size()
            );
        }catch (Exception exception){exception.printStackTrace();}
        finally {
            mutex.release();
            notFull.release();
        }
    }
}
