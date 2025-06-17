import java.util.*;

public class Polynomial implements Comparable<Polynomial>, Cloneable {

    private HashMap<Integer, Double> terms;

    public Polynomial() {
        terms = new HashMap<>();
    }

    public void setTerm(int degree, double coefficient) {
        if (coefficient != 0) {
            terms.put(degree, coefficient);
        } else {
            terms.remove(degree);
        }
    }

    public double getTerm(int degree) {
        return terms.getOrDefault(degree, 0.0);
    }

    public Polynomial add(Polynomial other) {
        Polynomial result = new Polynomial();
        Set<Integer> allDegrees = new HashSet<>(terms.keySet());
        allDegrees.addAll(other.terms.keySet());

        for (int degree : allDegrees) {
            double sum = this.getTerm(degree) + other.getTerm(degree);
            result.setTerm(degree, sum);
        }
        return result;
    }

    public Polynomial subtract(Polynomial other) {
        Polynomial result = new Polynomial();
        Set<Integer> allDegrees = new HashSet<>(terms.keySet());
        allDegrees.addAll(other.terms.keySet());

        for (int degree : allDegrees) {
            double diff = this.getTerm(degree) - other.getTerm(degree);
            result.setTerm(degree, diff);
        }
        return result;
    }

    public Polynomial multiplyByConstant(double constant) {
        Polynomial result = new Polynomial();
        for (Map.Entry<Integer, Double> entry : terms.entrySet()) {
            result.setTerm(entry.getKey(), entry.getValue() * constant);
        }
        return result;
    }

    public Polynomial multiply(Polynomial other) {
        Polynomial result = new Polynomial();
        for (Map.Entry<Integer, Double> e1 : this.terms.entrySet()) {
            for (Map.Entry<Integer, Double> e2 : other.terms.entrySet()) {
                int degree = e1.getKey() + e2.getKey();
                double coef = e1.getValue() * e2.getValue();
                result.setTerm(degree, result.getTerm(degree) + coef);
            }
        }
        return result;
    }

    public Polynomial divide(Polynomial divisor) {
        Polynomial dividend = this.clone();
        Polynomial result = new Polynomial();

        while (!dividend.isZero() && dividend.getDegree() >= divisor.getDegree()) {
            int degDiff = dividend.getDegree() - divisor.getDegree();
            double coefRatio = dividend.getLeadingCoefficient() / divisor.getLeadingCoefficient();

            Polynomial term = new Polynomial();
            term.setTerm(degDiff, coefRatio);
            result = result.add(term);

            Polynomial subtract = divisor.multiply(term);
            dividend = dividend.subtract(subtract);
        }
        return result;
    }

    public Polynomial remainder(Polynomial divisor) {
        Polynomial dividend = this.clone();

        while (!dividend.isZero() && dividend.getDegree() >= divisor.getDegree()) {
            int degDiff = dividend.getDegree() - divisor.getDegree();
            double coefRatio = dividend.getLeadingCoefficient() / divisor.getLeadingCoefficient();

            Polynomial term = new Polynomial();
            term.setTerm(degDiff, coefRatio);
            Polynomial subtract = divisor.multiply(term);
            dividend = dividend.subtract(subtract);
        }
        return dividend;
    }

    public int getDegree() {
        return terms.isEmpty() ? 0 : Collections.max(terms.keySet());
    }

    public double getLeadingCoefficient() {
        return getTerm(getDegree());
    }

    public boolean isZero() {
        return terms.isEmpty();
    }

    @Override
    public String toString() {
        if (terms.isEmpty()) return "0";

        StringBuilder sb = new StringBuilder();
        List<Integer> degrees = new ArrayList<>(terms.keySet());
        degrees.sort(Collections.reverseOrder());

        for (int degree : degrees) {
            double coef = terms.get(degree);
            if (coef == 0) continue;

            if (sb.length() > 0) sb.append(coef >= 0 ? " + " : " - ");
            else if (coef < 0) sb.
            append("-");

            coef = Math.abs(coef);
            if (degree == 0) sb.append(coef);
            else if (degree == 1) sb.append(coef).append("x");
            else sb.append(coef).append("x^").append(degree);
        }
        return sb.toString();
    }

    @Override
    public int compareTo(Polynomial other) {
        return Integer.compare(this.getDegree(), other.getDegree());
    }

    @Override
    public Polynomial clone() {
        Polynomial copy = new Polynomial();
        for (Map.Entry<Integer, Double> entry : this.terms.entrySet()) {
            copy.setTerm(entry.getKey(), entry.getValue());
        }
        return copy;
    }
}
