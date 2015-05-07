namespace paa5
{
	public interface ISolver
	{
		State Solve();

		void UpdateFormula(Formula formula);
	}
}
