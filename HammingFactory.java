package dataFlow;

import java.util.concurrent.BlockingQueue;

public interface HammingFactory 
{
	public Hamming build(int hammingNumber, BlockingQueue<Integer> inputQueue, BlockingQueue<Integer> outputQueue);
}
