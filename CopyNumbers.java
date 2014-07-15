package dataFlow;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public interface CopyNumbers 
{
	public static CopyFactory factory = new CopyFactoryImpl();
}

class CopyFactoryImpl implements CopyFactory
{
	public Copy build(int seed, int numbersToPrint, BlockingQueue<Integer> inputQueue, List<BlockingQueue<Integer>> outputQueues)
	{
		return new CopyImpl(seed, numbersToPrint, inputQueue, outputQueues);
	}
}

class CopyImpl implements Copy 
{
	private int seed;
	private int numbersToPrint;
	private int count = 1;
	private final List<BlockingQueue<Integer>> outputQueues;
	private final BlockingQueue<Integer> inputQueue;
	
	CopyImpl(int seed, int numbersToPrint, BlockingQueue<Integer> inputQueue, List<BlockingQueue<Integer>> outputQueues)
	{
		this.seed = seed;
		this.numbersToPrint = numbersToPrint;
		this.inputQueue = inputQueue;
		this.outputQueues = outputQueues;
	}
	
	public Runnable start()
	{
		return new Runnable(){
			public void run()
			{
				try{
					System.out.println("Hamming Number: " + seed);
					for(BlockingQueue<Integer> h: outputQueues)
						h.put(seed);
					
					while(true)
					{
						if(count >= numbersToPrint)	
							throw new InterruptedException();
						
						seed = inputQueue.take();
						System.out.println("Hamming Number: " + seed);
						count++;			
						for(BlockingQueue<Integer> h: outputQueues)
						{
							h.put(seed);
						}
					}
				}catch(InterruptedException e){
					System.out.println("We have printed " + count + " hamming numbers.");
					Main.exec.shutdownNow();
					System.out.println("Shutting down CopyNumbers thread.");
				}
			}
		};
	}
}
