using System;
using System.Collections.Generic;
using System.Linq;

namespace paa5
{
	public class Formula
	{
		private readonly IList<Clause> clauses; 

		public Formula(IList<Clause> clauses)
		{
			this.clauses = clauses;
		}

		public int ClausesCount
		{
			get { return clauses.Count; }
		}

		public bool Satisfied(State state)
		{
			return clauses.All(clause => clause.Satisfied(state)); // All clauses must be satisfied
		}

		public int AverageWeight(State state)
		{
			return clauses.Sum(clause => clause.GetWeight(state))/clauses.Count;
		}

		// Number of unsatisfied clauses
		public int NotSatisfied(State state)
		{
			return clauses.Count(clause => !clause.Satisfied(state));
		}

		public int Penalty(State state, int method, int formulaWeight)
		{
			switch (method)
			{
				case 0:
					return 0;
				case 1: // formula weight - unsatisfied * average clause weight
					//Console.WriteLine("Penalty: {0}", formulaWeight - NotSatisfied(state) * AverageWeight(state));
					return formulaWeight - NotSatisfied(state)*AverageWeight(state);
				default:
					return 0;
			}
		}

		public void Print()
		{
			Console.WriteLine("Printing a formula:");

			foreach (var clause in clauses)
			{
				Console.WriteLine(clause);
			}
		}
	}
}
