import { Pipe, PipeTransform } from '@angular/core';
import { SPLIT_SEPARATOR } from './../constants/token.constants';

@Pipe({
    name: 'split'
})
export class SplitPipe implements PipeTransform {
    transform(input: any, separator: string = SPLIT_SEPARATOR, limit?: number): any {
        return input ? input.split(separator, limit) : input;
    }
}
