using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Text;

namespace paa5
{
	class Program
	{
		private static ClauseReader reader;
		private static ClauseParser parser;
		private static int varCount;
		private static int[] weights;
		private static List<Clause> clauses;
		private static Formula formula;
		private static string clause;
		private static ISolver bruteSolver;
		private static AnnealingSolver annealingSolver;

		private static int count;
		private static double timeSum = 0;
		private static double errSum = 0;
		private static int numberOfResults;
		private static int numberOfFails;
		private static double tempSum = 0;

		static void Main(string[] args)
		{
			reader = new ClauseReader(5);
			var outputFile = new StreamWriter(reader.GetPath() + "\\output5.txt");
			//WeightsGenerator.Generate(reader.GetPath());
			reader.SetOutputFile(outputFile);
			varCount = reader.GetVariablesCount();
			weights = reader.GetWeights();
			clauses = new List<Clause>();
			parser = new ClauseParser(weights);
			clause = reader.ReadNextClause();

			bruteSolver = new BruteForceSolver(varCount, weights, formula);
			annealingSolver = new AnnealingSolver(varCount, weights, formula);

			while (clause != "end")
			{
				while (!clause.StartsWith("----- ") && clause != "end") // read whole formula
				{
					var parsedClause = parser.Parse(clause);
					clauses.Add(parsedClause);

					clause = reader.ReadNextClause();
				} 
				// whole formula has been read

				formula = new Formula(clauses);
				//bruteSolver.UpdateFormula(formula);
				//State bruteResult = bruteSolver.Solve();
				
				annealingSolver.UpdateFormula(formula);
				var annealSW = Stopwatch.StartNew();
			    State annealResult = annealingSolver.Solve();
				annealSW.Stop();

				numberOfResults++;
				count++;
				tempSum += annealingSolver.CalculateInitTemperature(2);
				if (annealResult != null)
				{
					//State bruteResult = bruteSolver.Solve();
					//var relError = Math.Abs((bruteResult.Weight - annealResult.Weight) / (float)bruteResult.Weight * 100);
					StringBuilder sb = new StringBuilder();
					sb.Append(annealResult.ToString());
					//sb.Append(System.Environment.NewLine);
					//sb.Append(bruteResult.ToString());
					sb.Append(System.Environment.NewLine);
					//sb.Append(", rel error: ");
					//sb.Append(relError);
					//sb.Append(" %");
					sb.Append(", anneal time: ");
					sb.Append(annealSW.Elapsed.TotalMilliseconds);
					sb.Append(" ms");
					OutputWriter.PrintOutput(sb.ToString(), outputFile);
					
					timeSum += annealSW.Elapsed.TotalMilliseconds;
					//errSum += relError;
				}
				else
				{
					numberOfFails++;
					timeSum += annealSW.Elapsed.TotalMilliseconds;
					OutputWriter.PrintOutput("No solution found. time: " + annealSW.Elapsed.TotalMilliseconds + " ms", outputFile);
				}
				Console.WriteLine("--------------------------");
				//formula.Print();

				varCount = reader.GetVariablesCount();
				weights = reader.GetWeights();
				clauses = new List<Clause>();
				parser = new ClauseParser(weights);
				//bruteSolver = new BruteForceSolver(varCount, weights, formula);
				//OutputWriter.PrintOutput(bruteResult.ToString(), outputFile);
				//OutputWriter.PrintOutput(annealResult.ToString(), outputFile);
				annealingSolver = new AnnealingSolver(varCount, weights, formula);

				clause = reader.ReadNextClause(); // if there are no files, we'll get "end". If there is still something to read, we'll get a clause.
			}

			Console.WriteLine("Total number of formulas: {0}, total number of fails: {1}, success rate: {2}", numberOfResults, numberOfFails, 100 - (((float)numberOfFails / numberOfResults) * 100));
			Console.WriteLine("Average time: {0}, average temp: {1}", timeSum / count, tempSum / count);
		}
	}
}
