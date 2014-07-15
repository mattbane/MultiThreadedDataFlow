package dataFlow;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public interface MergeNumbers 
{
	public static MergeFactory factory = new MergeFactoryImpl();
}

class MergeFactoryImpl implements MergeFactory
{
	public Merge build(List<BlockingQueue<Integer>> inputQueues, BlockingQueue<Integer> outputQueue)
	{
		return new MergeImpl(inputQueues, outputQueue);
	}
}

class MergeImpl implements Merge
{
	private final List<BlockingQueue<Integer>> inputQueues;
	private final BlockingQueue<Integer> outputQueue;
	
	public MergeImpl(List<BlockingQueue<Integer>> inputQueues, BlockingQueue<Integer> outputQueue)
	{
		this.inputQueues = inputQueues;
		this.outputQueue = outputQueue;
	}
	
	private void findOutputNumbers() throws InterruptedException
	{
		BlockingQueue<Integer> minQueue = new LinkedBlockingQueue<Integer>();
		int min = Integer.MAX_VALUE;
		int num;
		for(BlockingQueue<Integer> bq : inputQueues)
		{
			if(bq.peek() == null)
				return;
			else if((num = bq.peek()) <= min)
			{
				min = num;
				minQueue = bq;
			}
		}
		int numToPut = minQueue.take();
		outputQueue.put(numToPut);
		
		//remove duplicates
		for(BlockingQueue<Integer> bq : inputQueues)
		{
			if (bq.peek() != null && bq.peek() == numToPut)
				bq.take();
		}
	}
	
	public Runnable start()
	{
		return new Runnable(){
			public void run()
			{
				try{
					while(!Thread.interrupted())
						findOutputNumbers();					
					throw new InterruptedException();
				}catch(InterruptedException ex)
				{
					Main.exec.shutdownNow();
					System.out.println("IException thrown. Merge thread shut down.");
				}
			}
		};
	}
}
