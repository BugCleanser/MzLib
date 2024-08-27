package mz.mzlib.test;

import mz.mzlib.util.async.AsyncTask;

public class TestContext
{
    public AsyncTask controller=new AsyncTask();

    public AsyncTask getController()
    {
        return this.controller;
    }
}
