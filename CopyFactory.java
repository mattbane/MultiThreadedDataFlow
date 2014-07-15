package dataFlow;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public interface CopyFactory 
{
	public Copy build(int seed, int numbersToPrint, BlockingQueue<Integer> inputQueue, List<BlockingQueue<Integer>> outputQueues);
}
