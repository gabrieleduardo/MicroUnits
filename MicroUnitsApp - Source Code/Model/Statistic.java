/**
 * MicroUnits - Pauses Analysis of XML files generated by Translog II software.
 * For Translog II details See <http://bridge.cbs.dk/platform/?q=Translog-II>
 *
 * Copyright (C) 2014 Gabriel Ed. da Silva
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package Model;

import java.util.ArrayList;

/**
 *
 * @author Gabriel Ed
 * @param <T>  Numeric class
 */
public class Statistic<T extends Number> {

    /**
     *  Average
     *  Média Aritmética
     *
     * @param list Number ArrayList
     * @return Double value with the Average
     */
    public Double average(ArrayList<T> list) {
        if (list.isEmpty()) {
            return 0.0;
        }

        Double sum = 0.0;
        for (T x : list) {
            sum += x.doubleValue();
        }

        return sum / list.size();
    }

    /**
     * Standard Deviation
     * Desvio Padrão
     *
     * @param list Numeric ArrayList
     * @param average Average
     * @return Double value with the standard deviation
     */
    public Double standardDeviation(ArrayList<T> list, T average) {
        Double sum = 0.0;

        if (list.size() <= 1) {
            return 0.0;
        }

        for (T x : list) {
            sum = Math.pow(x.doubleValue() - average.doubleValue(), 2.0);
        }

        return Math.sqrt(sum / (list.size() - 1.0));
    }

    /**
     * Max value
     * Valor Máximo
     *
     * @param list Numeric ArrayList
     * @return The Max value
     */
    public Double max(ArrayList<T> list) {
        if (list.isEmpty()) {
            return null;
        }

        Double max = Double.MIN_VALUE;

        for (T x : list) {
            if (x.doubleValue() > max) {
                max = x.doubleValue();
            }
        }
        return max;
    }

    /**
     * Min value
     * Valor Mínimo
     *
     * @param list Numeric ArrayList
     * @return Min Value
     */
    public Double min(ArrayList<T> list) {
        if (list.isEmpty()) {
            return null;
        }

        Double min = Double.MAX_VALUE;

        for (T x : list) {
            if (x.doubleValue() < min) {
                min = x.doubleValue();
            }
        }
        return min;
    }
}
