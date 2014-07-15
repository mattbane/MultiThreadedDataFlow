package dataFlow;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public interface MergeFactory 
{
	public Merge build(List<BlockingQueue<Integer>> inputQueues, BlockingQueue<Integer> outputQueue);
}
