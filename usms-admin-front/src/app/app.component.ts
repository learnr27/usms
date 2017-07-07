import {Component} from '@angular/core';
import {HttpService} from "./core/service/http.service";
import {Message} from 'primeng/primeng';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  // 消息提示
  messages: Message[] = [];

  constructor(private httpService: HttpService) {
    /*监听提示信息变化*/
    this.httpService.messages$.subscribe(msgs => {
      this.messages = msgs;
    });
  }

}