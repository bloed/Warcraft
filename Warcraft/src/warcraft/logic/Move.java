package warcraft.logic;


public class Move {
    //class used as a repository of the move of the form : degree|id|action|value
    public Move(Double pDegree, Integer pId, String pAction, Double pValue){
        _Degree = pDegree;
        _Id = pId;
        _Action = pAction;
        _Value = pValue;
    }
    public Double getDegree(){
        return _Degree;
    }
    public Integer getId(){
        return _Id;
    }
    public String getAction(){
        return _Action;
    }
    public Double getValue(){
        return _Value;
    }
    @Override
    public String toString(){
        return _Degree + "|" + _Id + "|" + _Action + "|" + _Value;
    }
    private final Double _Degree;
    private final Integer _Id;
    private final String _Action;
    private final Double _Value;
}
