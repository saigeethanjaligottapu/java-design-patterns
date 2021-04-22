package com.iluwatar.clientserver;

public class RunnableSocket implements Runnable {
  private Thread t;
  private String role;
  RunnableSocket(String role){
    this.role = role;
  }
  public void start(){
    if(t==null){
      t = new Thread(this,role);
      t.start();
    }
  }
  @Override
  public void run() {
    if(this.role.equals("Client")){
      Client.main(new String[]{});
    }
    else {
      Server.main(new String[]{});
      try {
        Thread.sleep(1000,100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
