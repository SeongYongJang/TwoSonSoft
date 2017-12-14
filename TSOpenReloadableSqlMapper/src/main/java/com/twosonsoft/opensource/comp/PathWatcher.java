package com.twosonsoft.opensource.comp;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;

import com.sun.nio.file.SensitivityWatchEventModifier;

public final class PathWatcher
{
	final static Kind<Path> ENTRY_CREATE = StandardWatchEventKinds.ENTRY_CREATE;
	final static Kind<Path> ENTRY_DELETE = StandardWatchEventKinds.ENTRY_DELETE;
	final static Kind<Path> ENTRY_MODIFY = StandardWatchEventKinds.ENTRY_MODIFY;
	final static Kind<Object> OVERFLOW = StandardWatchEventKinds.OVERFLOW;

	private String watchPath;
	private WatchService watcher = null;

	private static PathWatcher singleton = new PathWatcher();

	List<InfPathWatcher> observer = new ArrayList<InfPathWatcher>();

	private PathWatcher()
	{
	}

	public static PathWatcher getInstance()
	{

		return singleton;
	}

	public void addObserver(InfPathWatcher watcher)
	{
		observer.add(watcher);
	}

	public void setPathParameter(String watchPath)
	{
		this.watchPath = watchPath;

	}

	public void closeWatcher() throws Exception
	{
		if (watcher != null)
		{
			watcher.close();
		}
	}

	public void runWatcher() throws Exception
	{
		Thread worker = new Thread()
		{

			@Override
			public void run()
			{

				try
				{
					watcher = FileSystems.getDefault().newWatchService();
					Path dir = Paths.get(watchPath);
					dir.register(watcher, new WatchEvent.Kind[] { ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY }, SensitivityWatchEventModifier.HIGH);
					// watch path
					while (true)
					{
						WatchKey key;
						String targetFilename = "";
						try
						{
							// wait for a key to be available
							key = watcher.take();
						}
						catch (InterruptedException ex)
						{
							return;
						}
						for (WatchEvent<?> event : key.pollEvents())
						{
							// get event type
							WatchEvent.Kind<?> kind = event.kind();
							// get file name
							@SuppressWarnings("unchecked")
							WatchEvent<Path> ev = (WatchEvent<Path>) event;
							Path fileName = ev.context();

							if (kind == OVERFLOW)
							{
								continue;
							}
							else if (kind == ENTRY_CREATE || kind == ENTRY_DELETE || kind == ENTRY_MODIFY)
							{
								// process event
								targetFilename = watchPath + fileName.toString();
							}

						} // end of for

						// IMPORTANT: The key must be reset after processed
						boolean valid = key.reset();

						// notify path-change to all observer
						if (observer != null)
						{
							// notify to all observer
							for (InfPathWatcher watcher : observer)
							{
								watcher.nofitfyPathChange(watchPath, targetFilename);
							}

						}
						if (!valid)
						{
							break;
						}
					} // end of while
				}
				catch (Exception e)
				{
					try
					{
						if (watcher != null)
						{
							watcher.close();
						}
					}
					catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		};
		worker.start();
	}
}
