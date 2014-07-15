package dataFlow;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.*;

public class Main 
{
	public final static ExecutorService exec = Executors.newFixedThreadPool(5);
	
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the number of Hamming numbers you would like see: ");
		int numberOfHammingNumbers = sc.nextInt();
		
		BlockingQueue<Integer> outputMerger = new LinkedBlockingQueue<Integer>();
		
		BlockingQueue<Integer> inputMerger2 = new LinkedBlockingQueue<Integer>();
		BlockingQueue<Integer> inputMerger3 = new LinkedBlockingQueue<Integer>();
		BlockingQueue<Integer> inputMerger5 = new LinkedBlockingQueue<Integer>();
		
		List<BlockingQueue<Integer>> hammingOutput = new ArrayList<BlockingQueue<Integer>>();
		hammingOutput.add(inputMerger2);
		hammingOutput.add(inputMerger3);
		hammingOutput.add(inputMerger5);
		
		MergeFactory mergeFactory = MergeNumbers.factory;
		Merge merge = mergeFactory.build(hammingOutput, outputMerger);
		
		BlockingQueue<Integer> inputHamming2 = new LinkedBlockingQueue<Integer>();
		BlockingQueue<Integer> inputHamming3 = new LinkedBlockingQueue<Integer>();
		BlockingQueue<Integer> inputHamming5 = new LinkedBlockingQueue<Integer>();
		
		List<BlockingQueue<Integer>> hammingInputs = new ArrayList<BlockingQueue<Integer>>();
		hammingInputs.add(inputHamming2);
		hammingInputs.add(inputHamming3);
		hammingInputs.add(inputHamming5);
		
		CopyFactory copyFactory = CopyNumbers.factory;
		Copy copy = copyFactory.build(1, numberOfHammingNumbers, outputMerger, hammingInputs);
		
		HammingFactory hammingFactory = HammingNumbers.factory;
		Hamming hamming2 = hammingFactory.build(2, inputHamming2, inputMerger2);
		Hamming hamming3 = hammingFactory.build(3, inputHamming3, inputMerger3);
		Hamming hamming5 = hammingFactory.build(5, inputHamming5, inputMerger5);
		
		//Start threads
		exec.execute(merge.start());
		exec.execute(hamming2.start());
		exec.execute(hamming3.start());
		exec.execute(hamming5.start());
		exec.execute(copy.start());
	}
}
