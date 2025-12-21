package mz.mzlib.tester;

import mz.mzlib.util.async.AsyncTask;

public class TesterContext
{
    public AsyncTask controller = new AsyncTask();

    public int level;
    public TesterContext(int level)
    {
        this.level = level;
    }

    public AsyncTask getController()
    {
        return this.controller;
    }
}
