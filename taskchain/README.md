# taskchain
taskchain是一个流式多线程任务执行器。

taskchain由多个Node组成，每个Node负责执行整体任务中的一个阶段。

每个Node都会启动一个线程池，多线程执行任务。