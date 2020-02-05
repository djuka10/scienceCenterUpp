import { TaskService } from './../services/task-service/task.service';
import { TokenStorageService } from './../services/auth/token-storage.service';
import { Router } from '@angular/router';
import { Task } from './../model/task';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit {

  private tasks: Task[] = null;
  private role: string[];

  constructor(private taskService: TaskService, private router: Router, private tokenService: TokenStorageService) { }

  ngOnInit() {
    this.role = this.tokenService.getAuthorities();
    let x = this.taskService.getAllUserTask(this.tokenService.getUser());

    x.subscribe(res => {
      this.tasks = res;
    }, error =>  {

    });

  }

  view(task: Task){
    let link: string = '/' + task.url + '/' +  task.parameter;
    this.router.navigate([link]);
  }

  removeTask(task: Task){
    let x = this.taskService.removeTask(task.taskId);

    x.subscribe(res => {
      let i: number = this.tasks.indexOf(task);
      this.tasks.splice(i, 1);
    }, erro => {
      
    });
  }

}
