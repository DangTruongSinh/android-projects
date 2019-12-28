package com.truongsinh.freekingmath;

import java.util.Random;

public class GenerateLevel {
    private static final int EASY =10;
    private static final int MEDIUM =25;
    private static final int HARD = 40;
    public static ModelLevel generateLevel(int core)
    {
        ModelLevel modelLevel = new ModelLevel();

        if(core < EASY)
            modelLevel.difficultLevel = 0;
        else if(core < MEDIUM)
            modelLevel.difficultLevel = 1;
        else if(core < HARD)
            modelLevel.difficultLevel = 2;
        else
            modelLevel.difficultLevel =3;

        Random random = new Random();
        // create random x,y
        modelLevel.x = random.nextInt(ModelLevel.arrMaxOperator[modelLevel.difficultLevel])+1;
        modelLevel.y = random.nextInt(ModelLevel.arrMaxOperator[modelLevel.difficultLevel])+1;
        // create random Correct , Wrong
        modelLevel.correctWrong = random.nextBoolean();
        // create random operator
        modelLevel.operator = random.nextInt(modelLevel.difficultLevel+1);
        // check
        modelLevel.operator_txt=ModelLevel.arrOperator[modelLevel.operator];
        if(modelLevel.correctWrong == true)
        {
            switch (modelLevel.operator)
            {
                case ModelLevel.ADD:
                    modelLevel.result = modelLevel.x+modelLevel.y;
                    break;
                case ModelLevel.SUB:
                    modelLevel.result= modelLevel.x - modelLevel.y;
                    break;
                case ModelLevel.MUL:
                    modelLevel.result = modelLevel.x*modelLevel.y;
                    break;
                default:
                    modelLevel.result = modelLevel.x/modelLevel.y;
            }
        }
        else
        {
            if(modelLevel.operator == ModelLevel.ADD)
                do {
                    modelLevel.result = random.nextInt(modelLevel.x+modelLevel.y+2);
                }while (modelLevel.result == modelLevel.x+modelLevel.y);
            else if(modelLevel.operator == ModelLevel.SUB)
                do {
                    modelLevel.result = random.nextInt(ModelLevel.arrMaxOperator[modelLevel.difficultLevel]);
                }while (modelLevel.result == modelLevel.x-modelLevel.y);
            else if(modelLevel.operator == ModelLevel.MUL)
                do {
                    modelLevel.result = random.nextInt(modelLevel.x*modelLevel.y+2);
                }while (modelLevel.result == modelLevel.x*modelLevel.y);
            else
                do {
                    modelLevel.result = random.nextInt(modelLevel.x/modelLevel.y+2);
                }while (modelLevel.result == modelLevel.x/modelLevel.y);
        }
        modelLevel.operator_txt = modelLevel.x + ModelLevel.arrOperator[modelLevel.operator]+modelLevel.y;
        modelLevel.resul_txt = modelLevel.EQUAL +modelLevel.result;

        return modelLevel;

    }
}
