package com.example.appcontact

import android.os.Bundle
import android.provider.ContactsContract.Contacts
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.appcontact.model.Contact
import com.example.appcontact.model.ContactDataBase
import com.example.appcontact.ui.theme.AppContactTheme
import com.example.appcontact.view.NewContactScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val db = Room.databaseBuilder(applicationContext, ContactDataBase::class.java, "contact").build()
        val viewModel = MainActivityViewModel(db)
        super.onCreate(savedInstanceState)
        setContent {
            AppContactTheme {
                // A surface container using the 'background' color from the theme
                MainApp(viewModel = viewModel)
            }
        }
    }
}


@Composable
fun MainApp(modifier: Modifier = Modifier,viewModel: MainActivityViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Home") {
        composable("Home") {
            Greeting(viewModel = viewModel, onFloatingClick = {navController.navigate("New contact")})
        }
        composable("New contact") {
            NewContactScreen(modifier = modifier, onCancelClick = {
                navController.popBackStack()
            }, onOkClick = {
                    name, phoneNum ->
                    viewModel.insert(Contact(name = name, phoneNumber = phoneNum))
                    navController.popBackStack()
            })
        }
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Greeting(modifier: Modifier = Modifier, viewModel: MainActivityViewModel, onFloatingClick: ()->Unit = {}) {
    val uiState = viewModel.uiState.collectAsState()
    val group = uiState.value.contacts.groupBy { it.name[0] }
    var searchVisible by remember {
        mutableStateOf(false)
    }
    var searchText by remember {
        mutableStateOf("")
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                AnimatedVisibility(visible = !searchVisible) {
                    Text(text = "Contacts", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                }
                AnimatedVisibility(visible = searchVisible) {
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it
                                            viewModel.find(searchText)},
                        placeholder = { Text("Search...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }


                              }, actions = {
                IconButton(onClick = { searchVisible = !searchVisible }) {
                    Icon(imageVector = Icons.Default.Search, null)
                }
            })
        },
        floatingActionButton = { FloatingActionButton(onClick = onFloatingClick) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }},
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(10.dp)) {
            group.forEach {
                    (initial, contactsForInitial) ->
                stickyHeader {
                    Text(initial.toString().uppercase(), modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color(0xffe6eaf0)
                        )
                        .padding(start = 20.dp, end = 20.dp), color = Color.Blue)
                }
                items(contactsForInitial) {
                        contact -> ContactItem(contact)
                }
            }
        }

    }
}


@Composable
fun ContactItem(contact: Contact = Contact(0,"", "")) {
    Card(colors = CardDefaults.cardColors(Color.White)) {
        Row (modifier = Modifier.fillMaxWidth()){
            Image(imageVector = ImageVector.vectorResource(id = R.drawable.baseline_person_24),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(start = 10.dp, end = 5.dp)
                    .clip(CircleShape)
                    .background(Color.Cyan)
                    .size(58.dp))

            Text(text = contact.name, modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
                .padding(5.dp), fontSize = 24.sp)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ContactScreenPreview() {
    AppContactTheme {
        ContactItem()
    }
}
