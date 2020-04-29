package com.imooc;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.format.number.CurrencyFormatter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Locale;

/**
 * Created by geely on 2015/11/22.
 */
public class FormatterTest {

    @Test
    public void test1() throws ParseException {
        CurrencyFormatter currencyFormatter = new CurrencyFormatter();
        currencyFormatter.setFractionDigits(2);//保留小数点后几位
        currencyFormatter.setRoundingMode(RoundingMode.CEILING);//舍入模式（ceilling表示四舍五入）
        //1、将带货币符号的字符"¥123.23" BigDecimal("123.00")
        System.out.println(new BigDecimal("123.13")+"  "+ currencyFormatter.parse("$123.125", Locale.US));
        Assert.assertEquals(new BigDecimal("123.13"), currencyFormatter.parse("$123.125", Locale.US));
    }
    @Test
    public void test2(){
        CurrencyFormatter currencyFormatter = new CurrencyFormatter();
        currencyFormatter.setFractionDigits(2);
        currencyFormatter.setRoundingMode(RoundingMode.CEILING);
        Assert.assertEquals("$123.00", currencyFormatter.print(new BigDecimal("123"), Locale.US));
    }
}
