package vn.edu.hust.studentman

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

  private val students = mutableListOf(
    StudentModel("Nguyễn Minh Tuấn", "20210001"),
    StudentModel("Trần Thị Hồng", "20210002"),
    StudentModel("Lê Văn Bảo", "20210003"),
    StudentModel("Phạm Thị Thanh", "20210004"),
    StudentModel("Đỗ Ngọc Sơn", "20210005"),
    StudentModel("Vũ Văn Duy", "20210006"),
    StudentModel("Hoàng Thị Yến", "20210007"),
    StudentModel("Bùi Văn Phát", "20210008"),
    StudentModel("Đinh Thị Hương", "20210009"),
    StudentModel("Nguyễn Quang Hải", "20210010"),
    StudentModel("Phạm Ngọc Anh", "20210011"),
    StudentModel("Trần Thị Lan", "20210012"),
    StudentModel("Lê Thị Phượng", "20210013"),
    StudentModel("Vũ Minh Hoàng", "20210014"),
    StudentModel("Hoàng Văn Đức", "20210015"),
    StudentModel("Đỗ Thị Mai", "20210016"),
    StudentModel("Nguyễn Thị Oanh", "20210017"),
    StudentModel("Trần Quốc Khánh", "20210018"),
    StudentModel("Phạm Thị Tuyền", "20210019"),
    StudentModel("Lê Hoàng Phúc", "20210020")
  )


  lateinit var studentAdapter: StudentAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    studentAdapter = StudentAdapter(
      this,
      students,
      onEditClick = { student ->
        openEditStudentFragment(student)
      },
      onRemoveClick = { student ->
        students.remove(student)
        studentAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Removed ${student.studentName}", Toast.LENGTH_SHORT).show()
      }
    )

    val listView = findViewById<ListView>(R.id.list_view_students)
    listView.adapter = studentAdapter

    registerForContextMenu(listView)

    listView.setOnItemClickListener { _, _, position, _ ->
      val student = students[position]
      openEditStudentFragment(student)
    }

    findViewById<Button>(R.id.btn_add_new).setOnClickListener {
      openAddStudentFragment()
    }
  }

  private fun openAddStudentFragment() {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(R.id.container, AddStudentFragment())
    transaction.addToBackStack(null)
    transaction.commit()
  }

  private fun openEditStudentFragment(student: StudentModel) {
    val transaction = supportFragmentManager.beginTransaction()
    val fragment = EditStudentFragment.newInstance(student)
    transaction.replace(R.id.container, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
  }

  fun addStudent(newStudent: StudentModel) {
    students.add(newStudent)
    studentAdapter.notifyDataSetChanged()  // Cập nhật lại ListView
  }

  override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
    super.onCreateContextMenu(menu, v, menuInfo)
    menu.setHeaderTitle("Options")
    menu.add(0, 0, 0, "Edit")
    menu.add(0, 1, 1, "Remove")
  }

  override fun onContextItemSelected(item: MenuItem): Boolean {
    val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
    val student = students[info.position]

    when (item.itemId) {
      0 -> {
        // Edit option selected
        openEditStudentFragment(student)
        return true
      }
      1 -> {
        // Remove option selected
        students.remove(student)
        studentAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Removed ${student.studentName}", Toast.LENGTH_SHORT).show()
        return true
      }
    }
    return super.onContextItemSelected(item)
  }
}

