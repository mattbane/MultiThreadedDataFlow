package dataFlow;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public interface HammingNumbers 
{
	public static HammingFactory factory = new HammingFactoryImpl();
}

class HammingFactoryImpl implements HammingFactory
{
	public Hamming build(int number, BlockingQueue<Integer> inputQueue, BlockingQueue<Integer> outputQueue)
	{
		return new HammingImpl(number, inputQueue, outputQueue);
	}
}

class HammingImpl implements Hamming
{
	private final BlockingQueue<Integer> hammingQueueInput;
	private final BlockingQueue<Integer> hammingQueueOutput;
	private final int hammingNumber;
	
	public HammingImpl(int hammingNumber, BlockingQueue<Integer> hammingQueueInput, BlockingQueue<Integer> hammingQueueOutput)
	{
		this.hammingNumber = hammingNumber;
		this.hammingQueueInput = hammingQueueInput;
		this.hammingQueueOutput = hammingQueueOutput;
	}
	
	public Runnable start()
	{
		return new Runnable(){
			public void run(){
				try
				{
					while(!Thread.interrupted()) 
						hammingQueueOutput.put(hammingNumber * hammingQueueInput.take()); 
				
					throw new InterruptedException();
				}
				catch(InterruptedException e)
				{				
					Main.exec.shutdownNow();
					System.out.println("Hamming Nummber thread IExeption. HammingNumbers thread shut down.");
				}
			}
		};
	}
}
