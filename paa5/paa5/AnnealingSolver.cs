using System;
using System.Linq;

namespace paa5
{
	class AnnealingSolver : ISolver
	{
		private int bestWeight;
		private State bestState;
		private int varCount;
		private int[] weights;
		private Formula formula;

		// annealing variables
		private const double THRESHOLD = 1;
		private const int STEPS_MAX = 50;
		private const double COOLDOWN = 0.9995;

		private long iteration = 0;
		private double temperature;
		private int steps = STEPS_MAX;

		private Random rand;

		public AnnealingSolver(int varCount, int[] weights, Formula formula)
		{
			bestWeight = 0;
			bestState = null;
			this.weights = weights;
			this.varCount = varCount;
			this.formula = formula;
		}

		public State Solve()
		{
			rand = new Random();
			temperature = CalculateInitTemperature(2);
			Console.WriteLine("Init temperature: {0}", temperature);
			var state = new State(varCount, 0);
			var weight = state.CalculateWeight(weights);
			//var file = new StreamWriter(@"C:\dev\paa\5\gnuplot\data_gnuplot_" + varCount + "_steps" + STEPS_MAX + "_temp" + temperature + "_cooldown" + COOLDOWN + ".txt");

			while (temperature > THRESHOLD)
			{
				while (steps > 0)
				{
					state = GenerateNeighbourState(state);

					if (state.Weight > bestWeight && formula.Satisfied(state))
					{
						bestWeight = state.Weight;
						bestState = state;
					}

					//if (iteration%20 == 0)
						//GnuplotOutput.Output(iteration++, state.Weight, file);
					//else iteration++;
					steps--;
				}

				steps = STEPS_MAX;
				temperature *= COOLDOWN;
				iteration++;
			}

			//file.Close();

			if (bestState != null)
			{
				bestState.Weight = bestWeight;
			}

			return bestState;
		}

		private State GenerateNeighbourState(State state)
		{
			int item = rand.Next(varCount); // 0 ... n (exclusive)
			var generatedState = new State(state, item, weights); // create a new state with an inverted bit

			int deltaWeight = generatedState.Weight - state.Weight;
			if (deltaWeight >= 0) return generatedState; // better weight -> return

			double probability = Math.Exp(deltaWeight / temperature); // e^delta/T TODO: minus!!!
			if (rand.NextDouble() < probability)
			{
				return generatedState;
			} // probability check

			return state; // probability check fails - return the old state
		}

		public double CalculateInitTemperature(int method)
		{
			switch (method)
			{
				case 0: // clause count * literal count 
					return formula.ClausesCount*varCount;
				case 1: // clause count * literal count * weights sum
					return formula.ClausesCount*varCount*weights.Sum();
				case 2: // (clause count / literal count) * weights sum
					//Console.WriteLine("clauses: {0}, varCount: {1}, sum: {2}", formula.ClausesCount, varCount, weights.Sum());
					return ((float)formula.ClausesCount/varCount)*weights.Sum() * 3;
				default:
					return 1000; // whatever
			}
		}

		public void UpdateFormula(Formula formula)
		{
			this.formula = formula;
		}
	}
}
