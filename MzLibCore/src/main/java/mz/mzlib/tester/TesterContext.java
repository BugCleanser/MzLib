package mz.mzlib.tester;

import mz.mzlib.util.async.AsyncTask;

public class TesterContext
{
    public AsyncTask controller=new AsyncTask();

    public AsyncTask getController()
    {
        return this.controller;
    }
}
