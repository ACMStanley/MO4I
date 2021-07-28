package uk.ac.ncl.astanley.mo4i.evaluators;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

import uk.ac.ncl.astanley.mo4i.main.Client;
import uk.ac.ncl.astanley.mo4i.util.Constants;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@SuppressWarnings("serial")
public class MO4IMultiThreadEvaluator<S> implements SolutionListEvaluator<S> {

  private final int numberOfThreads;
  
  public MO4IMultiThreadEvaluator(int numberOfThreads) {
    if (numberOfThreads == 0) {
      this.numberOfThreads = Runtime.getRuntime().availableProcessors();
    } else {
      this.numberOfThreads = numberOfThreads;
      System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism",
          "" + this.numberOfThreads);
    }
  }

  @Override
  public List<S> evaluate(List<S> solutionList, Problem<S> problem) {
    String COEPath = Client.getCOEPath();
    File logFile = new File(COEPath + "/coe.log");
    
    //Delete COE log each time it approaches maximum size to avoid thread safety issues
    long maxSize = Constants.COE_LOG_ARCHIVE_CEILING;
    if(logFile.exists()) {
		if(logFile.length() > maxSize) {
			System.out.println("\"coe.log\" has exceeded " + maxSize/1000000 + "MBs");
			System.out.println("Deleted \"coe.log\".");
			logFile.delete();
		}
		
    }
    
    solutionList.parallelStream().forEach(problem::evaluate);
    
    return solutionList;
  }

  public int getNumberOfThreads() {
    return numberOfThreads;
  }

  @Override
  public void shutdown() {
    //This method is an intentionally-blank override.
  }
}