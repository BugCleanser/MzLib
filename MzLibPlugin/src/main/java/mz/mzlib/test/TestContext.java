package mz.mzlib.test;

import mz.mzlib.util.async.TaskController;

public class TestContext
{
    public TaskController controller=new TaskController();

    public TaskController getController()
    {
        return this.controller;
    }
}
