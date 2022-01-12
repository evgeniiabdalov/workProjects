#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include <string.h>


#define memoryString "HELLO WORLD"

struct DictionaryElement{
   char symbol;
   unsigned int  signalSize;
   const bool* codingSignal;
};

struct DictionaryElement* elements;


void initializeDictionary(void){

    const static bool H_signal[] = {false, false, false, false};
    const static bool E_signal[] = {false};
    const static bool L_signal[] = {false, true, false, false};
    const static bool O_signal[] = {true, true, true};
    const static bool W_signal[] = {false, true, true};
    const static bool R_signal[] = {false, true, false};
    const static bool D_signal[] = {true, false, false};  
 
    //-------------------------------------------------------------------

    struct DictionaryElement H_Element;
    
    H_Element.symbol = 'H';
    H_Element.signalSize = 4;
    H_Element.codingSignal = H_signal; 
   
    struct DictionaryElement E_Element;
    
    E_Element.symbol = 'E';
    E_Element.signalSize = 1;
    E_Element.codingSignal = E_signal;
    

    struct DictionaryElement L_Element;
    
    L_Element.symbol = 'L';
    L_Element.signalSize = 4;
    L_Element.codingSignal = L_signal;

    struct DictionaryElement O_Element;    
     
    O_Element.symbol = 'O';
    O_Element.signalSize = 3;
    O_Element.codingSignal = O_signal;  

    struct DictionaryElement W_Element;

    W_Element.symbol = 'W';
    W_Element.signalSize = 3;
    W_Element.codingSignal = W_signal;

    struct DictionaryElement R_Element;
    
    R_Element.symbol = 'R';
    R_Element.signalSize = 3;
    R_Element.codingSignal = R_signal;

    struct DictionaryElement D_Element;
    
    D_Element.symbol = 'D';
    D_Element.signalSize = 3;
    D_Element.codingSignal = D_signal;

    //-------------------------------------------------------------------------

    elements = calloc(7, sizeof(struct DictionaryElement));

    elements[0] = H_Element;
    elements[1] = E_Element;
    elements[2] = L_Element;
    elements[3] = O_Element;
    elements[4] = W_Element;
    elements[5] = R_Element;
    elements[6] = D_Element;

}


volatile uint8_t pin_out = 0;

void delay_ms(uint32_t delay)
{
   return;
}

void set_pin_active(void)
{
   pin_out = 1;
   return;
}


void set_pin_inactive(void)
{
   pin_out = 0;
   return;
}

void sendLongSignal(void){

   set_pin_active();
   delay_ms(1500);
   set_pin_inactive();

   printf("%s ", "-");
   

}

void sendShortSignal(void){

   set_pin_active();
   delay_ms(500);
   set_pin_inactive();

   printf("%s ", ".");


}

void sendSeparatorSignal(void){

   delay_ms(500);  

}

void sendShortSeparatorSignal(void){

   printf(" ");
   delay_ms(1500);   

}

void sendLongSeparatorSignal(void){

   printf("    ");
   delay_ms(3500);
   

}

void playSequence(const bool* sequence, int sequenceSize){

     for(unsigned int i = 0; i < sequenceSize; i++){

        if( sequence[i] ){
          sendLongSignal();          
        }
        else{
          sendShortSignal();          
        }

        sendSeparatorSignal();

     }

      

}


void sendSequence(void){

     unsigned int messageSize = strlen(memoryString);  

     for(unsigned int i = 0; i < messageSize; i++){
      
         for(unsigned int j = 0; j < 7; j++){
            
            if( memoryString[i] == elements[j].symbol ){

               playSequence(elements[j].codingSignal, elements[j].signalSize);
               sendShortSeparatorSignal();
               break;               
            
            }
            else if( memoryString[i] == ' ' ){
               sendLongSeparatorSignal();
               break;               
            }

         }

     }
     printf("\n");      

}


int main(void){


   initializeDictionary();

   sendSequence();
   


}
