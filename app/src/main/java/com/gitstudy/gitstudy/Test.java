package com.gitstudy.gitstudy;

public class Test extends MyIntentService{
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public Test(String name) {
        super(name);
    }

    MyIntentService test = new MyIntentService("");
    String tt = test.testA;

    MyIntentService mMyIntentService = new MyIntentService("");
    MyIntentService.TestAdapter mtestAdapter = mMyIntentService.new TestAdapter();
    MyIntentService.TestAdapterB b = new MyIntentService.TestAdapterB();
}
